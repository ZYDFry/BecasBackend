package pe.idat.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "postulacion")
public class PostulacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Criterios de evaluación 
    private Double promedioNotas;
    private Double ingresosFamiliares;
    
    private String estado; // APROBADO, RECHAZADO, PENDIENTE
    private LocalDateTime fechaPostulacion;
    //Ranking
    private Double puntaje;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity estudiante;

    @ManyToOne
    @JoinColumn(name = "beca_id")
    private BecaEntity beca;

    @PrePersist
    public void prePersist() {
        this.fechaPostulacion = LocalDateTime.now();
        if(this.estado == null) this.estado = "PENDIENTE";
    }
}