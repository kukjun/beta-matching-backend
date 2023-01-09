package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface MissionRepository extends JpaRepository<Mission, UUID>, MissionCustomRepository {


    @Override
    Optional<Mission> findById(UUID uuid);

    @Override
    List<Mission> findAll();

    @Query("select m from Mission m where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate")
    List<Mission> findApplyMissions(@Param("currentDate") LocalDate currentDate);

    // 만들었는데 사용하지를 않음
//    @Query("select distinct m "+
//            "from Mission m "+
//            "join m.applyInformationList a join a.tester te " +
//            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id = :testerId")
//    List<Mission> findAppliedMissionsByTesterId(
//            @Param("testerId") UUID testerId,
//            @Param("currentDate") LocalDate currentDate
//    );

    @Query("select m " +
            "from Mission m " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate " +
            "and m <> (select m from Mission m join m.applyInformationList a join a.tester te where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id = :testerId)")
    List<Mission> findApplyMissionsExceptTesterId(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );


    @Query("select m " +
            "from Mission m " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate " +
            "and m <> (select m from Mission m join m.applyInformationList a join a.tester te where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id = :testerId) " +
            "order by m.createDateTime")
    List<Mission> findApplyMissionsExceptTesterIdByCreated(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );

    @Query("select m " +
            "from Mission m " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate " +
            "and m <> (select m from Mission m join m.applyInformationList a join a.tester te where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id = :testerId) " +
            "order by m.applyInformationList.size desc")
    List<Mission> findApplyMissionsExceptTesterIdByPopular(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );


    @Query("select m " +
            "from Mission m " +
            "where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate " +
            "and m <> (select m from Mission m join m.applyInformationList a join a.tester te where m.missionDate.recruitmentTimeStart <= :currentDate and m.missionDate.recruitmentTimeEnd >= :currentDate and te.id = :testerId) " +
            "order by m.missionDate.recruitmentTimeEnd-:currentDate")
    List<Mission> findApplyMissionsExceptTesterIdByDeadLine(
            @Param("testerId") UUID testerId,
            @Param("currentDate") LocalDate currentDate
    );

    @Query("select m from Mission m join m.maker ma where ma.id = :makerId")
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


}
