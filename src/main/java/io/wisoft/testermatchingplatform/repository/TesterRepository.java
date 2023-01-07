package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.handler.exception.service.TesterNotFoundException;
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

    public Tester findById(UUID id) {
        Tester tester = em.find(Tester.class, id);
        if (tester == null) {
            throw new TesterNotFoundException("id: " + id + " not found");
        }
        return tester;
    }

    public Tester findByEmail(String email) {
        List<Tester> testerList = em.createQuery("select t from Tester t where t.email=:email", Tester.class)
                .setParameter("email", email)
                .getResultList();
        if (testerList.size() == 0) {
            throw new TesterNotFoundException("email: " + email + " not found");
        }
        return testerList.get(0);
    }

    public List<Tester> findAll() {
        return em.createQuery(
                "select t from Tester t",
                Tester.class
        ).getResultList();
    }

    public int findAllCount() {
        return em.createQuery(
                "select t from Tester t",
                Tester.class
        ).getResultList().size();
    }

}
