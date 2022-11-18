package io.wisoft.testermatchingplatform.domain.test;

import io.wisoft.testermatchingplatform.web.dto.response.tester.dto.PopularDto;
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

    @Query(value = "select t.id,t.title,t.participant_capacity,t.register_time,t.content," + "t.reward,t.symbol_image_root,t.modify_time,t.maker_id," + "t.recruitment_time_start,t.recruitment_time_limit," + "t.duration_time_start,t.duration_time_limit " + "from test t, apply_information a " + "where a.test_id = t.id and t.recruitment_time_limit >= now()" + "group by t.id order by count(t.id) desc limit 4", nativeQuery = true)
    List<Test> getTop4Test();

    @EntityGraph("TestWithMaker")
    @Query(value = "select t from Test t where t.testRelateTime.recruitmentTimeLimit >= current_date")
    List<Test> findAllByTest();


    @EntityGraph("TestWithMaker")
    List<Test> findTop4ByTestRelateTime_RecruitmentTimeLimitGreaterThanEqualOrderByTestRelateTime_RecruitmentTimeLimit(Date date);

    List<Test> findByMaker_Id(UUID id);

    @Query(value = "select * from test t, " + "(select test.id from test where test.recruitment_time_start <= now() and test.recruitment_time_limit >= now() " + "EXCEPT select t.id from test t, apply_information a " + "where t.recruitment_time_start <= now() and t.recruitment_time_limit >= now() and t.id = a.test_id and a.tester_id = ?1) " + "as tt where t.id = tt.id order by now() - t.recruitment_time_limit;", nativeQuery = true)
    List<Test> findAllByTestByDeadLineExceptApply(UUID tester_id);

    @Query(value = "select * from test t, (select test.id from test where test.recruitment_time_start <= now() and test.recruitment_time_limit >= now() EXCEPT select t.id from test t, apply_information a where t.recruitment_time_start <= now() and t.recruitment_time_limit >= now() and t.id = a.test_id     and a.tester_id = ?1) as tt where t.id = tt.id;", nativeQuery = true)
    List<Test> findAllByTestByPopularExceptApply(UUID tester_id);


    @Query(value = "select * from test t, " + "(select test.id from test where test.recruitment_time_start <= now() and test.recruitment_time_limit >= now() " + "EXCEPT select t.id from test t, apply_information a " + "where t.recruitment_time_start <= now() and t.recruitment_time_limit >= now() and t.id = a.test_id and a.tester_id = ?1) " + "as tt where t.id = tt.id " + "order by t.register_time;", nativeQuery = true)
    List<Test> findAllByTestByCreatedExceptApply(UUID tester_id);

    @Query(value = "select * from test t, " + "(select test.id from test where test.recruitment_time_start <= now() and test.recruitment_time_limit >= now() " + "EXCEPT select t.id from test t, apply_information a " + "where t.recruitment_time_start <= now() and t.recruitment_time_limit >= now() and t.id = a.test_id and a.tester_id = ?1) " + "as tt where t.id = tt.id and t.title like concat('%', ?2,'%')" + "order by t.register_time", nativeQuery = true)
    List<Test> findAllByTestByTitleExceptApply(UUID tester_id, String title);

}
