package pe.idat.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.DTO.*;
import pe.idat.Entity.*;
import pe.idat.Mapper.PostulacionMapper;
import pe.idat.Repository.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulacionService {

    @Autowired private PostulacionRepository postulacionRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private BecaRepository becaRepo;
    @Autowired private PostulacionMapper mapper;

    public String registrarPostulacion(PostulacionDTO dto, String username) {
        // 1. Buscar entidades
        UsuarioEntity estudiante = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        BecaEntity beca = becaRepo.findById(dto.getIdBeca())
                .orElseThrow(() -> new RuntimeException("Beca no encontrada"));

        // 2. Validar Vigencia (Fechas)
        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(beca.getFechaInicio())) throw new RuntimeException("La convocatoria aún no inicia.");
        if (hoy.isAfter(beca.getFechaFin())) throw new RuntimeException("La convocatoria ya cerró.");
        if (!beca.getActiva()) throw new RuntimeException("La beca está inactiva.");

        // 3. Mapear DTO a Entidad
        PostulacionEntity post = mapper.toEntity(dto);
        post.setEstudiante(estudiante);
        post.setBeca(beca);

        // 4. LÓGICA DE NEGOCIO: Evaluación Automática
        // Regla: Nota >= 16 y Pobreza (Ingresos < 2000) -> APROBADO
        if (dto.getPromedioNotas() >= 16.0 && dto.getIngresosFamiliares() < 2000.00) {
            post.setEstado("APROBADO");
        } 
        // Regla: Nota muy baja -> RECHAZADO
        else if (dto.getPromedioNotas() < 13.0) {
            post.setEstado("RECHAZADO");
        } 
        // Resto -> PENDIENTE (Revisión manual)
        else {
            post.setEstado("PENDIENTE");
        }

        postulacionRepo.save(post);
        return "Solicitud registrada. Estado preliminar: " + post.getEstado();
    }

    // Listar Ranking (Para Admin)
    public List<PostulacionResponseDTO> listarRanking() {
        return postulacionRepo.findAllByOrderByPromedioNotasDesc()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Listar Mis Solicitudes (Para Estudiante)
    public List<PostulacionResponseDTO> listarMisPostulaciones(String username) {
        return postulacionRepo.findByEstudiante_Username(username)
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}