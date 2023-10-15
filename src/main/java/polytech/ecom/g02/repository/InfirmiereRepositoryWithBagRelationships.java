package polytech.ecom.g02.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import polytech.ecom.g02.domain.Infirmiere;

public interface InfirmiereRepositoryWithBagRelationships {
    Optional<Infirmiere> fetchBagRelationships(Optional<Infirmiere> infirmiere);

    List<Infirmiere> fetchBagRelationships(List<Infirmiere> infirmieres);

    Page<Infirmiere> fetchBagRelationships(Page<Infirmiere> infirmieres);
}
