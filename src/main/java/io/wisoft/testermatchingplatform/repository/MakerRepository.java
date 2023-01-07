package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.handler.exception.service.MakerNotFoundException;
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
        Maker maker = em.find(Maker.class, id);
        if (maker == null) {
            throw new MakerNotFoundException("id: " + id + " not found");
        }
        return maker;
    }

    public Maker findByEmail(String email) throws NoResultException, NonUniqueResultException {
        List<Maker> makerList = em.createQuery(
                        "select m from Maker m where m.email = :email",
                        Maker.class
                ).setParameter("email", email)
                .getResultList();
        if (makerList.size() == 0) {
            throw new MakerNotFoundException("email: " + email + " not found");
        }
        return makerList.get(0);
    }

    public List<Maker> findAll() {
        return em.createQuery("select m from Maker m", Maker.class)
                .getResultList();
    }

    public int findAllCount() {
        return em.createQuery("select m from Maker m", Maker.class)
                .getResultList().size();
    }

}
