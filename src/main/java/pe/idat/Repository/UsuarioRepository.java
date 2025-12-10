package pe.idat.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.idat.Entity.UsuarioEntity;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}