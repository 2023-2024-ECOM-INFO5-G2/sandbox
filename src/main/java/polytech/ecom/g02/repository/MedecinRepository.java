package polytech.ecom.g02.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import polytech.ecom.g02.domain.Medecin;

/**
 * Spring Data JPA repository for the Medecin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {}
