package io.wisoft.testermatchingplatform.domain.applyinformation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplyInformationRepository extends JpaRepository<ApplyInformation, UUID> {

    @Query(value = "select a.test.id from ApplyInformation a group by a.test.id order by count(a.test.id)")
    List<UUID> getTop4Test();

    int countByTestId(UUID id);


    @Query(value = "SELECT COUNT(a.id) > 0 FROM ApplyInformation a WHERE a.id=?1 AND a.approveTime is not null")
    boolean isApprove(UUID id);

    List<ApplyInformation> findByTestId(UUID id);


    @Query(value = "select a.test.id from ApplyInformation a,Test t " +
            "where a.test.id = t.id AND a.tester.id = ?1 AND t.testRelateTime.recruitmentTimeStart <= current_date AND t.testRelateTime.recruitmentTimeLimit >= current_date")
    List<UUID> getOnlyApplyTestId(UUID id);

    @Query(value = "select a.test.id from ApplyInformation a,Test t " +
            "where a.test.id = t.id AND a.tester.id = ?1 AND a.approveCheck = true AND t.testRelateTime.recruitmentTimeLimit < current_date AND t.testRelateTime.durationTimeStart > current_date")
    List<UUID> getApproveTestId(UUID id);


    @Query(value = "select a.test.id from ApplyInformation a,Test t " +
            "where a.test.id = t.id AND a.tester.id = ?1 AND a.approveCheck = true AND t.testRelateTime.recruitmentTimeStart <= current_date AND t.testRelateTime.durationTimeLimit >= current_date")
    List<UUID> getProgressTestId(UUID id);

    @Query(value = "select a.test.id from ApplyInformation a,Test t " +
            "where a.test.id = t.id AND a.tester.id = ?1 AND a.completeCheck = false AND t.testRelateTime.durationTimeLimit < current_date")
    List<UUID> getNotCompleteTestId(UUID id);


    @Query(value = "select a.test.id from ApplyInformation a,Test t " +
            "where a.test.id = t.id AND a.tester.id = ?1 AND a.completeCheck = true AND t.testRelateTime.durationTimeLimit < current_date")
    List<UUID> getCompleteTestId(UUID id);


}


