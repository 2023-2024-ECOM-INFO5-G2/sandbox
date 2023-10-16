package polytech.g2.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import polytech.g2.blog.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
