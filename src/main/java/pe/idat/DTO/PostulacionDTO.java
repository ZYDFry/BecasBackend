package pe.idat.DTO;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PostulacionDTO {

    @NotNull(message = "El ID de la beca es obligatorio")
    private Long idBeca;

    @NotNull(message = "El promedio es obligatorio")
    @DecimalMin(value = "0.0", message = "El promedio no puede ser menor a 0")
    @DecimalMax(value = "20.0", message = "El promedio no puede ser mayor a 20")
    private Double promedioNotas;

    @NotNull(message = "Los ingresos son obligatorios")
    @PositiveOrZero(message = "Los ingresos no pueden ser negativos")
    private Double ingresosFamiliares;
}