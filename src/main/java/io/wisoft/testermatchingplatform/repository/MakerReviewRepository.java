package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.MakerReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MakerReviewRepository {
    private final EntityManager em;

    public UUID save(MakerReview makerReview) {
        em.persist(makerReview);
        return makerReview.getId();
    }

    public MakerReview findOne(UUID id) {
        return em.find(MakerReview.class, id);
    }

    public List<MakerReview> findAll(UUID id) {
        return em.createQuery(
                "select mr from MakerReview mr",
                MakerReview.class
        ).getResultList();
    }
}
