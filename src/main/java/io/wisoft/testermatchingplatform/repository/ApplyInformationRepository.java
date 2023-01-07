package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.handler.exception.service.ApplyInformationNotDeleteException;
import io.wisoft.testermatchingplatform.handler.exception.service.ApplyInformationNotFoundException;
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

    public ApplyInformation findById(UUID id) {
        ApplyInformation applyInformation = em.find(ApplyInformation.class, id);
        if (applyInformation == null) {
            throw new ApplyInformationNotFoundException("id: " + id + " not found");
        }
        return applyInformation;
    }

    public List<ApplyInformation> findAll() {
        List<ApplyInformation> resultList = em.createQuery(
                "select a from ApplyInformation a",
                ApplyInformation.class
        ).getResultList();
        return resultList;
    }

    public List<ApplyInformation> findByTesterId(UUID testerId) {
        String jpql = "select a from ApplyInformation a where a.tester.id=:testerId";
        return em.createQuery(jpql, ApplyInformation.class)
                .setParameter("testerId", testerId)
                .getResultList();
    }

    public void deleteById(UUID applyInformationId) {
        List<ApplyInformation> applyInformationList = em.createQuery("delete from ApplyInformation a where a.id=:applyInformationId", ApplyInformation.class)
                .setParameter("applyInformationId", applyInformationId)
                .getResultList();
        if (applyInformationList.size() == 0) {
            throw new ApplyInformationNotDeleteException("id: " + applyInformationId + " can't delete");
        }
    }
}
