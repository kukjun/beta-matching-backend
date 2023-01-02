package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Comparator;
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

    public List<Tests> findApplyTests() {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select t from Tests t where t.applyInformationList >= :currentDate";
        return em.createQuery(jpql, Tests.class)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Tests> findAppliedTestsByTesterId(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct t " +
                "from Tests t " +
                "join t.applyInformationList a join a.tester te " +
                "where t.applyInformationList >= :currentDate and te.id = :testerId";
        return em.createQuery(
                        jpql,
                        Tests.class)
                .setParameter("currentDate", currentDate)
                .setParameter("testerId", testerId)
                .getResultList();
    }

    public List<Tests> findApplyTestsExceptTesterId(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct t from Tests t join t.applyInformationList a join a.tester te where t.applyInformationList >= :currentDate and te.id <> :testerId";
        return em.createQuery(jpql, Tests.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Tests> findApplyTestsExceptTesterIdByCreated(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct t from Tests t join t.applyInformationList a join a.tester te where t.applyInformationList >= :currentDate and te.id <> :testerId order by t.createDateTime";
        return em.createQuery(jpql, Tests.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Tests> findApplyTestsExceptTesterIdByPopular(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        // 가능한지 Test 필요
        String jpql = "select distinct t from Tests t join t.applyInformationList a join a.tester te where t.applyInformationList >= :currentDate and te.id <> :testerId order by count(t)";
        return em.createQuery(jpql, Tests.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();

    }

    public List<Tests> findApplyTestsExceptTesterIdByDeadLine(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        // 가능한지 Test 필요
        String jpql = "select distinct t from Tests t join t.applyInformationList a join a.tester te where t.applyInformationList >= :currentDate and te.id <> :testerId";
        List<Tests> resultList = em.createQuery(jpql, Tests.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();

        resultList.sort(Comparator.comparingInt(o -> o.getApplyInformationList().size()));
        return resultList;
    }

    public List<Tests> findAppliedTestsByMakerId(UUID makerId) {
        String jpql = "select t from Tests t join t.maker m where m.id = :makerId";
        List<Tests> resultList = em.createQuery(jpql, Tests.class)
                .setParameter("makerId", makerId)
                .getResultList();
        return resultList;
    }


//    public List<Tests> findPopularTop4() {
//        String jpql = "select t from Tests t join ApplyInformation a where t.testDate.recruitmentTimeEndgroup by t.id order by count(a)";
//        return em.createQuery(jpql, Tests.class)
//                .setFirstResult(0)
//                .setMaxResults(4)
//                .getResultList();
//
//    }

}
