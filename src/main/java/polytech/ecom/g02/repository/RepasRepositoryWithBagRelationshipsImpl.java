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
import polytech.ecom.g02.domain.Repas;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RepasRepositoryWithBagRelationshipsImpl implements RepasRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Repas> fetchBagRelationships(Optional<Repas> repas) {
        return repas.map(this::fetchIdRepas);
    }

    @Override
    public Page<Repas> fetchBagRelationships(Page<Repas> repas) {
        return new PageImpl<>(fetchBagRelationships(repas.getContent()), repas.getPageable(), repas.getTotalElements());
    }

    @Override
    public List<Repas> fetchBagRelationships(List<Repas> repas) {
        return Optional.of(repas).map(this::fetchIdRepas).orElse(Collections.emptyList());
    }

    Repas fetchIdRepas(Repas result) {
        return entityManager
            .createQuery("select repas from Repas repas left join fetch repas.idRepas where repas.id = :id", Repas.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Repas> fetchIdRepas(List<Repas> repas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, repas.size()).forEach(index -> order.put(repas.get(index).getId(), index));
        List<Repas> result = entityManager
            .createQuery("select repas from Repas repas left join fetch repas.idRepas where repas in :repas", Repas.class)
            .setParameter("repas", repas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
