package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Maker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MakerRepository {

    private final EntityManager em;

    public void save(Maker maker) {
        if (maker.getId() == null) {
            em.persist(maker);
        } else {
            // merge를 쓰는게 맞나?
            em.merge(maker);
        }
    }

    public Maker findOne(UUID id) {
        return em.find(Maker.class, id);
    }

    public List<Maker> findAll() {
        return em.createQuery("select m from Maker m", Maker.class)
                .getResultList();
    }
}
