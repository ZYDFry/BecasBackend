package pe.idat.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.idat.Entity.RolEntity;
import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
}