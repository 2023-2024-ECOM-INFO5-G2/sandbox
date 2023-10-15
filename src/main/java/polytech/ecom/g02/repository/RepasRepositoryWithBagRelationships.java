package polytech.ecom.g02.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import polytech.ecom.g02.domain.Repas;

public interface RepasRepositoryWithBagRelationships {
    Optional<Repas> fetchBagRelationships(Optional<Repas> repas);

    List<Repas> fetchBagRelationships(List<Repas> repas);

    Page<Repas> fetchBagRelationships(Page<Repas> repas);
}
