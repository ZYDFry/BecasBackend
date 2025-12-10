package pe.idat.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "beca")
public class BecaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    
    // Control de fechas
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activa;
}