package pe.idat.DTO;
import lombok.Data;

@Data 
public class PostulacionResponseDTO {
    private Long id;
    private String nombreBeca;
    private String nombreEstudiante;
    private Double promedioNotas;
    private Double ingresosFamiliares;
    private String estado;
    private java.time.LocalDateTime fechaPostulacion;
}