package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.handler.exception.service.MissionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MissionRepository {

    private final EntityManager em;

    public UUID save(Mission test) {
        em.persist(test);
        return test.getId();
    }

    public Mission findById(UUID id) {
        Mission mission = em.find(Mission.class, id);
        if (mission == null) {
            throw new MissionNotFoundException("id: " + id + " not found");
        }
        return mission;
    }

    public List<Mission> findAll() {
        return em.createQuery("select m from Mission m", Mission.class)
                .getResultList();
    }

    public List<Mission> findApplyMissions() {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select m from Mission m where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate";
        return em.createQuery(jpql, Mission.class)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Mission> findAppliedMissionsByTesterId(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct m " +
                "from Mission m " +
                "join m.applyInformationList a join a.tester te " +
                "where where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id = :testerId";
        return em.createQuery(
                        jpql,
                        Mission.class)
                .setParameter("currentDate", currentDate)
                .setParameter("testerId", testerId)
                .getResultList();
    }

    public List<Mission> findApplyMissionsExceptTesterId(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct m from Mission m join m.applyInformationList a join a.tester te where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id <> :testerId";
        return em.createQuery(jpql, Mission.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Mission> findApplyMissionsExceptTesterIdByCreated(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select distinct m from Mission m join m.applyInformationList a join a.tester te where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id <> :testerId order by m.createDateTime";
        return em.createQuery(jpql, Mission.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();
    }

    public List<Mission> findApplyMissionsExceptTesterIdByPopular(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        // 가능한지 Test 필요
        String jpql = "select distinct m from Mission m join m.applyInformationList a join a.tester te where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id <> :testerId order by count(m)";
        return em.createQuery(jpql, Mission.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();

    }

    public List<Mission> findApplyMissionsExceptTesterIdByDeadLine(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        // 가능한지 Test 필요
        String jpql = "select distinct m from Mission m join m.applyInformationList a join a.tester te where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id <> :testerId";
        List<Mission> resultList = em.createQuery(jpql, Mission.class)
                .setParameter("testerId", testerId)
                .setParameter("currentDate", currentDate)
                .getResultList();

        resultList.sort(Comparator.comparingInt(o -> o.getApplyInformationList().size()));
        return resultList;
    }

    public List<Mission> findAppliedMissionsByMakerId(UUID makerId) {
        String jpql = "select m from Mission m join m.maker ma where m.id = :makerId";
        List<Mission> resultList = em.createQuery(jpql, Mission.class)
                .setParameter("makerId", makerId)
                .getResultList();
        return resultList;
    }

    public int findProgressMission() {
        LocalDate currentDate = LocalDate.now();
        int size = em.createQuery("select m from Mission m where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.durationTimeEnd >= :currentDate")
                .setParameter("currentDate", currentDate)
                .getResultList().size();
        return size;
    }

    public int findCompleteMission() {
        LocalDate currentDate = LocalDate.now();
        int size = em.createQuery("select m from Mission m where m.missionDate.durationTimeEnd < :currentDate")
                .setParameter("currentDate", currentDate)
                .getResultList().size();
        return size;
    }

    // JPA 성능 최적화 필요
    public List<Mission> findDeadLineTop4Mission() {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select m " +
                "from Mission m " +
                "where m.missionDate.recruitmentTimeStart >= :currentDate " +
                "and m.missionDate.recruitmentTimeEnd<= :currentDate";
        List<Mission> missionList = em.createQuery(jpql, Mission.class)
                .setParameter("currentDate", currentDate)
                .getResultList();
        missionList.sort(Comparator.comparingLong(Mission::remainApplyDays));
        List<Mission> sortedList = new ArrayList<>();
        if (missionList.size() >= 4) {
            for (int i = 0; i < 4; i++) {
                sortedList.add(missionList.get(i));
            }
        } else {
            sortedList.addAll(missionList);
        }
        return sortedList;

    }

    public void findPopularTop4Mission() {
        LocalDate currentDate = LocalDate.now();
        String jpql = "select m " +
                "from Mission m " +
                "where m.missionDate.recruitmentTimeStart >= :currentDate " +
                "and m.missionDate.recruitmentTimeEnd<= :currentDate " +
                "order by m.applyInformationList.size";
        em.createQuery(jpql, Mission.class)
                .setParameter("currentDate", currentDate)
                .setFirstResult(0)
                .setMaxResults(4)
                .getResultList();
    }

}
