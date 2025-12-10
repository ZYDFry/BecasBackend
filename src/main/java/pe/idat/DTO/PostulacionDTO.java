package pe.idat.DTO;
import lombok.Data;
@Data 
public class PostulacionDTO {
    private Long idBeca;
    private Double promedioNotas;      // El alumno ingresa su promedio
    private Double ingresosFamiliares; // El alumno declara sus ingresos
}