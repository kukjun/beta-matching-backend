package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Maker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MakerRepository {

    private final EntityManager em;

    public UUID save(Maker maker) {
        em.persist(maker);
        return maker.getId();
    }

    public Maker findById(UUID id) {
        return em.find(Maker.class, id);
    }

    public Maker findByEmail(String email) throws NoResultException, NonUniqueResultException {
        return em.createQuery(
                        "select m from Maker m where m.email = :email",
                        Maker.class
                ).setParameter("email", email)
                .getSingleResult();
    }

    public List<Maker> findAll() {
        return em.createQuery("select m from Maker m", Maker.class)
                .getResultList();
    }
}
