package io.wisoft.testermatchingplatform.domain.test;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<Test, UUID> {

    @Query("SELECT COUNT(t) FROM Test t WHERE current_date > DATE(t.testRelateTime.durationTimeLimit)")
    Long countCompleteTest();

    @Query("SELECT COUNT(t) FROM Test t WHERE current_date >= DATE(t.testRelateTime.recruitmentTimeStart) AND current_date  <= DATE(t.testRelateTime.recruitmentTimeLimit)")
    Long countContinueTest();

    @EntityGraph("TestWithMaker")
    List<Test> findAllByOrderByRegisterTime();

    @EntityGraph("TestWithMaker")
    List<Test> findTop4ByTestRelateTime_DurationTimeLimitAfterOrderByTestRelateTime_DurationTimeLimit(Date date);
}
