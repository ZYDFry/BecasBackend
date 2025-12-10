package pe.idat.Mapper;

import org.springframework.stereotype.Component;
import pe.idat.DTO.PostulacionDTO;
import pe.idat.DTO.PostulacionResponseDTO;
import pe.idat.Entity.PostulacionEntity;

@Component
public class PostulacionMapper {

    // INPUT: DTO -> Entity
    public PostulacionEntity toEntity(PostulacionDTO dto) {
        PostulacionEntity entity = new PostulacionEntity();
        entity.setPromedioNotas(dto.getPromedioNotas());
        entity.setIngresosFamiliares(dto.getIngresosFamiliares());
        return entity;
    }

    // OUTPUT: Entity -> ResponseDto
    public PostulacionResponseDTO toResponseDTO(PostulacionEntity entity) {
        PostulacionResponseDTO dto = new PostulacionResponseDTO();
        dto.setId(entity.getId());
        dto.setPromedioNotas(entity.getPromedioNotas());
        dto.setIngresosFamiliares(entity.getIngresosFamiliares());
        dto.setEstado(entity.getEstado());
        dto.setFechaPostulacion(entity.getFechaPostulacion());
        
        if (entity.getBeca() != null) dto.setNombreBeca(entity.getBeca().getNombre());
        if (entity.getEstudiante() != null) dto.setNombreEstudiante(entity.getEstudiante().getNombreCompleto());
        
        return dto;
    }
}