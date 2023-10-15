package polytech.ecom.g02.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import polytech.ecom.g02.domain.AideSoignant;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AideSoignantRepositoryWithBagRelationshipsImpl implements AideSoignantRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AideSoignant> fetchBagRelationships(Optional<AideSoignant> aideSoignant) {
        return aideSoignant.map(this::fetchIdSoignants);
    }

    @Override
    public Page<AideSoignant> fetchBagRelationships(Page<AideSoignant> aideSoignants) {
        return new PageImpl<>(
            fetchBagRelationships(aideSoignants.getContent()),
            aideSoignants.getPageable(),
            aideSoignants.getTotalElements()
        );
    }

    @Override
    public List<AideSoignant> fetchBagRelationships(List<AideSoignant> aideSoignants) {
        return Optional.of(aideSoignants).map(this::fetchIdSoignants).orElse(Collections.emptyList());
    }

    AideSoignant fetchIdSoignants(AideSoignant result) {
        return entityManager
            .createQuery(
                "select aideSoignant from AideSoignant aideSoignant left join fetch aideSoignant.idSoignants where aideSoignant.id = :id",
                AideSoignant.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<AideSoignant> fetchIdSoignants(List<AideSoignant> aideSoignants) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, aideSoignants.size()).forEach(index -> order.put(aideSoignants.get(index).getId(), index));
        List<AideSoignant> result = entityManager
            .createQuery(
                "select aideSoignant from AideSoignant aideSoignant left join fetch aideSoignant.idSoignants where aideSoignant in :aideSoignants",
                AideSoignant.class
            )
            .setParameter("aideSoignants", aideSoignants)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
