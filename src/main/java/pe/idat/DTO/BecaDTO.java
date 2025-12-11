package pe.idat.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BecaDTO {
    private Long id; // Opcional para crear, necesario para editar
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activa;
}