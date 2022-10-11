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

    @Query(value =  "select t.id,t.title,t.participant_capacity,t.register_time,t.content," +
            "t.reward,t.symbol_image_root,t.modify_time,t.maker_id," +
            "t.recruitment_time_start,t.recruitment_time_limit," +
            "t.duration_time_start,t.duration_time_limit " +
            "from test t, apply_information a " +
            "where a.test_id = t.id " +
            "group by t.id order by count(t.id) desc limit 4", nativeQuery = true)
    List<Test> getTop4Test();


    @EntityGraph("TestWithMaker")
    List<Test> findAllByOrderByRegisterTime();

    @EntityGraph("TestWithMaker")
    List<Test> findTop4ByTestRelateTime_DurationTimeLimitAfterOrderByTestRelateTime_DurationTimeLimit(Date date);

    List<Test> findByMaker_Id(UUID id);
}
