package pe.idat.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.DTO.BecaDTO;
import pe.idat.Entity.BecaEntity;
import pe.idat.Repository.BecaRepository;
import java.util.List;
import pe.idat.Exceptions.RecursoNoEncontradoException;
import pe.idat.Exceptions.ReglaNegocioException;

@Service
public class BecaService {

    @Autowired
    private BecaRepository becaRepo;

    // 1. LISTAR TODAS (Para el Admin)
    public List<BecaEntity> listarTodas() {
        return becaRepo.findAll();
    }

    // 2. LISTAR SOLO ACTIVAS (Para el Estudiante - Catálogo)
    // Nota: Asegúrate de tener 'findByActivaTrue' en tu BecaRepository o usa un filter
    public List<BecaEntity> listarActivas() {
        // Opción rápida con Java Stream si no quieres tocar el Repo:
        return becaRepo.findAll().stream()
                .filter(BecaEntity::getActiva)
                .toList();
    }

    // 3. GUARDAR / CREAR BECA
    public BecaEntity guardarBeca(BecaDTO dto) {
        BecaEntity beca = new BecaEntity();
        
        // Si viene ID, es una edición
        if (dto.getId() != null) {
            beca = becaRepo.findById(dto.getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Beca no encontrada"));
        }

        beca.setNombre(dto.getNombre());
        beca.setDescripcion(dto.getDescripcion());
        beca.setFechaInicio(dto.getFechaInicio());
        beca.setFechaFin(dto.getFechaFin());
        
        // Por defecto activa si viene nulo
        beca.setActiva(dto.getActiva() != null ? dto.getActiva() : true);

        return becaRepo.save(beca);
    }
    
    // 4. ELIMINAR BECA
    public void eliminarBeca(Long id) {
        becaRepo.deleteById(id);
    }
}