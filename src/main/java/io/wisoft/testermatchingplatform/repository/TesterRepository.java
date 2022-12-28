package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TesterRepository {
    private final EntityManager em;

    public UUID save(Tester tester) {
        em.persist(tester);
        return tester.getId();
    }

    public Tester findOne(UUID id) {
        return em.find(Tester.class, id);
    }

    public List<Tester> findAll() {
        return em.createQuery(
                "select t from Tester t",
                Tester.class
        ).getResultList();
    }

}
