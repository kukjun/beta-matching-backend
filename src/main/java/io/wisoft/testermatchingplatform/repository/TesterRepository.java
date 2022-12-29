package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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

    public Tester findById(UUID id) {
        return em.find(Tester.class, id);
    }

    public Tester findByEmail(String email) throws NoResultException, NonUniqueResultException {
        Tester tester = em.createQuery("select t from Tester t where t.email=:email", Tester.class)
                .setParameter("email", email)
                .getSingleResult();
        return tester;
    }

    public List<Tester> findAll() {
        return em.createQuery(
                "select t from Tester t",
                Tester.class
        ).getResultList();
    }

}
