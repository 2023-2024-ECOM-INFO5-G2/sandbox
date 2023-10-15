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
import polytech.ecom.g02.domain.Infirmiere;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class InfirmiereRepositoryWithBagRelationshipsImpl implements InfirmiereRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Infirmiere> fetchBagRelationships(Optional<Infirmiere> infirmiere) {
        return infirmiere.map(this::fetchIdInfirmieres);
    }

    @Override
    public Page<Infirmiere> fetchBagRelationships(Page<Infirmiere> infirmieres) {
        return new PageImpl<>(fetchBagRelationships(infirmieres.getContent()), infirmieres.getPageable(), infirmieres.getTotalElements());
    }

    @Override
    public List<Infirmiere> fetchBagRelationships(List<Infirmiere> infirmieres) {
        return Optional.of(infirmieres).map(this::fetchIdInfirmieres).orElse(Collections.emptyList());
    }

    Infirmiere fetchIdInfirmieres(Infirmiere result) {
        return entityManager
            .createQuery(
                "select infirmiere from Infirmiere infirmiere left join fetch infirmiere.idInfirmieres where infirmiere.id = :id",
                Infirmiere.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Infirmiere> fetchIdInfirmieres(List<Infirmiere> infirmieres) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, infirmieres.size()).forEach(index -> order.put(infirmieres.get(index).getId(), index));
        List<Infirmiere> result = entityManager
            .createQuery(
                "select infirmiere from Infirmiere infirmiere left join fetch infirmiere.idInfirmieres where infirmiere in :infirmieres",
                Infirmiere.class
            )
            .setParameter("infirmieres", infirmieres)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
