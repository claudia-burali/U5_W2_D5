package claudiaburali.gestione_dispositivi.repositories;

import claudiaburali.gestione_dispositivi.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {
    Optional<Dipendente> findByUsername (String username);
    Optional<Dipendente> findByEmail (String email);
}

