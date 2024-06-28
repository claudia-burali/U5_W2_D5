package claudiaburali.gestione_dispositivi.repositories;
import claudiaburali.gestione_dispositivi.entities.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {
}
