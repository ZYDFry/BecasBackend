package pe.idat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.idat.Entity.PostulacionEntity;
import java.util.List;

@Repository
public interface PostulacionRepository extends JpaRepository<PostulacionEntity, Long> {

    // 1. Para que el ESTUDIANTE vea sus propias postulaciones
    // Spring busca dentro de la entidad 'Postulacion', el campo 'estudiante', y dentro el 'username'
    List<PostulacionEntity> findByEstudiante_Username(String username);

    // 2. Para el RANKING del ADMIN
    // Traducción: "Búscame por Estado (ej. PENDIENTE) y ordénalos por Puntaje de mayor a menor"
    // Esto es lo que permite que el alumno con más nota/necesidad aparezca primero.
    List<PostulacionEntity> findByEstadoOrderByPuntajeDesc(String estado);
}