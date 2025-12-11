package pe.idat.Mapper;

import org.springframework.stereotype.Component;
import pe.idat.DTO.PostulacionDTO;
import pe.idat.DTO.PostulacionResponseDTO;
import pe.idat.Entity.PostulacionEntity;

@Component
public class PostulacionMapper {

    // De DTO a Entidad (Registro)
    public PostulacionEntity toEntity(PostulacionDTO dto) {
        PostulacionEntity entity = new PostulacionEntity();
        entity.setPromedioNotas(dto.getPromedioNotas());
        entity.setIngresosFamiliares(dto.getIngresosFamiliares());
        // El puntaje NO se mapea aquí, lo calcula el Service
        return entity;
    }

    // De Entidad a ResponseDTO (Listado/Ranking)
    public PostulacionResponseDTO toResponseDTO(PostulacionEntity entity) {
        PostulacionResponseDTO dto = new PostulacionResponseDTO();
        dto.setId(entity.getId());
        
        // Mapeo seguro de relaciones
        if (entity.getEstudiante() != null) {
            dto.setNombreEstudiante(entity.getEstudiante().getNombreCompleto());
        }
        if (entity.getBeca() != null) {
            dto.setNombreBeca(entity.getBeca().getNombre());
        }

        dto.setPromedioNotas(entity.getPromedioNotas());
        dto.setIngresosFamiliares(entity.getIngresosFamiliares());
        dto.setEstado(entity.getEstado());
        dto.setFechaPostulacion(entity.getFechaPostulacion());
        
        // --- IMPORTANTE: MAPEAR EL PUNTAJE ---
        dto.setPuntaje(entity.getPuntaje());
        
        return dto;
    }
}