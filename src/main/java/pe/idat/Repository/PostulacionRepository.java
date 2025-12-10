package pe.idat.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.idat.Entity.PostulacionEntity;
import java.util.List;

public interface PostulacionRepository extends JpaRepository<PostulacionEntity, Long> {
    // Ver mis trámites
    List<PostulacionEntity> findByEstudiante_Username(String username);
    
    // Para el Ranking: Notas más altas primero
    List<PostulacionEntity> findAllByOrderByPromedioNotasDesc(); 
}