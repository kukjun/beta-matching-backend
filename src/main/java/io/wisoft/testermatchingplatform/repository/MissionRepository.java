package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface MissionRepository extends JpaRepository<Mission, UUID> {
    @Override
    Optional<Mission> findById(UUID uuid);

    @Override
    List<Mission> findAll();

    @Query("select m from Mission m where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate")
    List<Mission> findApplyMissions(@Param("currentDate") LocalDate currentDate);

    @Query("select distinct m "+
            "from Mission m "+
            "join m.applyInformationList a join a.tester te " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id = :testerId")
    List<Mission> findAppliedMissionsByTesterId(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );

    @Query("select distinct m " +
            "from Mission m " +
            "join m.applyInformationList a join a.tester te " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id <> :testerId")
    List<Mission> findApplyMissionsExceptTesterId(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );

    @Query("select distinct m " +
            "from Mission m " +
            "join m.applyInformationList a join a.tester te " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id <> :testerId " +
            "order by m.createDateTime")
    List<Mission> findApplyMissionsExceptTesterIdByCreated(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );

    @Query("select distinct m " +
            "from Mission m " +
            "join m.applyInformationList a join a.tester te " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id <> :testerId " +
            "order by count(m)")
    List<Mission> findApplyMissionsExceptTesterIdByPopular(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );


    // JPA 최신화와 Table 변경중 선택
    @Query("select distinct m " +
            "from Mission m " +
            "join m.applyInformationList a join a.tester te " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id <> :testerId")
    List<Mission> findApplyMissionsExceptTesterIdByDeadLine(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );

    @Query("select m from Mission m join m.maker ma where m.id = :makerId")
    List<Mission> findAppliedMissionsByMakerId(
            @Param("makerId") UUID makerId
    );

    @Query("select count(m) from Mission m where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.durationTimeEnd >= :currentDate")
    long findProgressMission(
            @Param("currentDate") LocalDate currentDate
    );

    @Query("select count(m) from Mission m where m.missionDate.durationTimeEnd < :currentDate")
    long findCompleteMission(
            @Param("currentDate") LocalDate currentDate
    );


    //    // JPA 성능 최적화 필요
//    public List<Mission> findDeadLineTop4Mission() {
//        LocalDate currentDate = LocalDate.now();
//        String jpql = "select m " +
//                "from Mission m " +
//                "where m.missionDate.recruitmentTimeStart >= :currentDate " +
//                "and m.missionDate.recruitmentTimeEnd<= :currentDate";
//        List<Mission> missionList = em.createQuery(jpql, Mission.class)
//                .setParameter("currentDate", currentDate)
//                .getResultList();
//        missionList.sort(Comparator.comparingLong(Mission::remainApplyDays));
//        List<Mission> sortedList = new ArrayList<>();
//        if (missionList.size() >= 4) {
//            for (int i = 0; i < 4; i++) {
//                sortedList.add(missionList.get(i));
//            }
//        } else {
//            sortedList.addAll(missionList);
//        }
//        return sortedList;
//
//    }
//
//    public void findPopularTop4Mission() {
//        LocalDate currentDate = LocalDate.now();
//        String jpql = "select m " +
//                "from Mission m " +
//                "where m.missionDate.recruitmentTimeStart >= :currentDate " +
//                "and m.missionDate.recruitmentTimeEnd<= :currentDate " +
//                "order by m.applyInformationList.size";
//        em.createQuery(jpql, Mission.class)
//                .setParameter("currentDate", currentDate)
//                .setFirstResult(0)
//                .setMaxResults(4)
//                .getResultList();
//    }


}
