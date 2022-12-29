package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TestsRepository {

    private final EntityManager em;

    public UUID save(Tests test) {
        em.persist(test);
        return test.getId();
    }

    public Tests findById(UUID id) {
        return em.find(Tests.class, id);
    }

    public List<Tests> findAll() {
        return em.createQuery("select t from Tests t", Tests.class)
                .getResultList();
    }

}
