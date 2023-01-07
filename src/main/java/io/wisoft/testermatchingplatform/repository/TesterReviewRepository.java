package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.TesterReview;
import io.wisoft.testermatchingplatform.handler.exception.service.TesterReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TesterReviewRepository{

    private final EntityManager em;

    public UUID save(TesterReview testerReview) {
        em.persist(testerReview);
        return testerReview.getId();
    }

    public TesterReview findById(UUID id) {
        TesterReview testerReview = em.find(TesterReview.class, id);
        if (testerReview == null) {
            throw new TesterReviewNotFoundException("id: " + id + " not found");
        }
        return testerReview;
    }

    public List<TesterReview> findAll(UUID id) {
        return em.createQuery(
                "select tr from TesterReview tr",
                TesterReview.class
        ).getResultList();
    }
}
