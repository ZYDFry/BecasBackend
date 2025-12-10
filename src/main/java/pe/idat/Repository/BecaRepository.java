package pe.idat.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.idat.Entity.BecaEntity;

public interface BecaRepository extends JpaRepository<BecaEntity, Long> {}