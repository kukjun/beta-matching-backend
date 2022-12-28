package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ApplyInformationRepository {

    private final EntityManager em;

    public UUID save(ApplyInformation applyInformation) {
        em.persist(applyInformation);
        return applyInformation.getId();
    }

    public ApplyInformation findOne(UUID id) {
        return em.find(ApplyInformation.class, id);
    }

    public List<ApplyInformation> findAll() {
        return em.createQuery(
                "select a from ApplyInformation a",
                ApplyInformation.class
        ).getResultList();
    }
}
