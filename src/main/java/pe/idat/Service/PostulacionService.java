package pe.idat.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.DTO.*;
import pe.idat.Entity.*;
import pe.idat.Exceptions.RecursoNoEncontradoException;
import pe.idat.Exceptions.ReglaNegocioException;
import pe.idat.Mapper.PostulacionMapper;
import pe.idat.Repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulacionService {

    @Autowired private PostulacionRepository postulacionRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private BecaRepository becaRepo;
    @Autowired private PostulacionMapper mapper;

    // =========================================================
    // 1. REGISTRAR POSTULACIÓN (Lógica Automática de Puntaje)
    // =========================================================
    public String registrarPostulacion(PostulacionDTO dto, String username) {
        
        // --- A. Validaciones Previas ---
        UsuarioEntity estudiante = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + username));
        
        BecaEntity beca = becaRepo.findById(dto.getIdBeca())
                .orElseThrow(() -> new RecursoNoEncontradoException("Beca no encontrada con ID: " + dto.getIdBeca()));

        // Validar Fechas (Usamos LocalDate porque la beca tiene fechas simples)
        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(beca.getFechaInicio())) throw new ReglaNegocioException("La convocatoria aún no inicia.");
        if (hoy.isAfter(beca.getFechaFin())) throw new ReglaNegocioException("La convocatoria ya cerró.");
        if (!beca.getActiva()) throw new ReglaNegocioException("La beca está inactiva.");

        // --- B. Mapeo de Datos ---
        PostulacionEntity post = mapper.toEntity(dto);
        post.setEstudiante(estudiante);
        post.setBeca(beca);
        
        // Usamos LocalDateTime para guardar fecha y hora exacta
        post.setFechaPostulacion(LocalDateTime.now()); 

        // --- C. CÁLCULO DEL RANKING (Algoritmo del Negocio) ---
        // Fórmula: Nota + Bonificación por Nivel Socioeconómico
        
        double puntajeFinal = dto.getPromedioNotas(); // Base = Nota del alumno
        double sueldoMinimo = 1025.0; // Referencia actual

        // Si gana menos del sueldo mínimo (Pobreza), sumamos 5 puntos
        if (dto.getIngresosFamiliares() <= sueldoMinimo) {
            puntajeFinal += 5.0; 
        } 
        // Si gana menos de 2 sueldos mínimos (Clase Media-Baja), sumamos 2 puntos
        else if (dto.getIngresosFamiliares() <= (sueldoMinimo * 2)) {
            puntajeFinal += 2.0; 
        }
        // Si gana más, se queda con su nota original.

        post.setPuntaje(puntajeFinal); // Guardamos el cálculo en la BD

        // --- D. DETERMINAR ESTADO INICIAL ---
        
        // Filtro 1: Rechazo Automático por nota muy baja
        if (dto.getPromedioNotas() < 13.0) {
            post.setEstado("RECHAZADO");
        } 
        // Filtro 2: Pasa a "PENDIENTE" para que el Admin decida en el Ranking
        else {
            post.setEstado("PENDIENTE");
        }

        postulacionRepo.save(post);
        
        if (post.getEstado().equals("RECHAZADO")) {
            return "Lo sentimos, tu promedio (menor a 13.0) no cumple con los requisitos mínimos.";
        }
        
        return "Solicitud registrada. Tu puntaje de ranking preliminar es: " + puntajeFinal;
    }

    // =========================================================
    // 2. LISTAR RANKING (Vista del Admin)
    // =========================================================
    public List<PostulacionResponseDTO> listarRanking() {
        // Buscamos solo los PENDIENTES y los ordenamos por el PUNTAJE calculado (Mayor a menor)
        return postulacionRepo.findByEstadoOrderByPuntajeDesc("PENDIENTE")
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // =========================================================
    // 3. EVALUAR POSTULACIÓN (Decisión del Admin)
    // =========================================================
    public String evaluarPostulacion(Long idPostulacion, String nuevoEstado) {
        PostulacionEntity post = postulacionRepo.findById(idPostulacion)
                .orElseThrow(() -> new RecursoNoEncontradoException("Postulación no encontrada"));

        // Solo permitimos estados finales válidos
        if (!nuevoEstado.equals("APROBADO") && !nuevoEstado.equals("RECHAZADO")) {
            throw new ReglaNegocioException("Estado inválido. Use APROBADO o RECHAZADO.");
        }

        post.setEstado(nuevoEstado);
        postulacionRepo.save(post);

        return "La postulación del alumno " + post.getEstudiante().getUsername() + 
               " ha sido actualizada a: " + nuevoEstado;
    }

    // =========================================================
    // 4. LISTAR MIS SOLICITUDES (Vista del Estudiante)
    // =========================================================
    public List<PostulacionResponseDTO> listarMisPostulaciones(String username) {
        return postulacionRepo.findByEstudiante_Username(username)
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}