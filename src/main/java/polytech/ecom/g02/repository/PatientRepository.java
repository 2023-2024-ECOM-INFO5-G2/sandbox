package polytech.ecom.g02.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import polytech.ecom.g02.domain.Patient;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {}
