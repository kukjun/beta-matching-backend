package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplyInformationRepository extends JpaRepository<ApplyInformation, UUID> {
    @Override
    Optional<ApplyInformation> findById(UUID uuid);

    Optional<ApplyInformation> findApplyInformationByTesterIdAndMissionId(UUID testerId, UUID missionId);

    @Override
    List<ApplyInformation> findAll();

    @Query("select a from ApplyInformation a where a.tester.id=:testerId")
    List<ApplyInformation> findByTesterId(@Param("testerId") UUID testerId);


    @Override
    void deleteById(UUID applyInformationId);

    void deleteApplyInformationByTesterIdAndMissionId(UUID testerId, UUID missionId);

    @Query("select count(a) from ApplyInformation a join a.mission m where m.id = :missionId and a.approveTime is not null")
    long existsApplyInformationByMissionIdAndApproveTimeIsNull(@Param("missionId") UUID missionId);

    @Query("select count(a) from ApplyInformation a join a.mission m where m.id = :missionId and a.executionTime is not null")
    long existsApplyInformationByMissionIdAndExecutionTimeIsNull(@Param("missionId")UUID missionId);
}
