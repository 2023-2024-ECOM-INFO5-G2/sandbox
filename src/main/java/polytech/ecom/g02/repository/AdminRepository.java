package polytech.ecom.g02.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import polytech.ecom.g02.domain.Admin;

/**
 * Spring Data JPA repository for the Admin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {}
