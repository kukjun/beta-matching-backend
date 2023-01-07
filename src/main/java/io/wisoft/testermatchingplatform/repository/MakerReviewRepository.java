package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.MakerReview;
import io.wisoft.testermatchingplatform.handler.exception.service.MakerReviewNotFoundException;
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

    public MakerReview findById(UUID id) {
        MakerReview makerReview = em.find(MakerReview.class, id);
        if (makerReview == null) {
            throw new MakerReviewNotFoundException("id: " + id + " not found");
        }
        return makerReview;
    }

    public List<MakerReview> findAll(UUID id) {
        return em.createQuery(
                "select mr from MakerReview mr",
                MakerReview.class
        ).getResultList();
    }
}
