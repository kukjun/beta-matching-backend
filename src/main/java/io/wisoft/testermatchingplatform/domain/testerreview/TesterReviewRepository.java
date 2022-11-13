package io.wisoft.testermatchingplatform.domain.testerreview;

import io.wisoft.testermatchingplatform.web.dto.response.maker.dto.SimpleReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TesterReviewRepository extends JpaRepository<TesterReview, UUID> {

    @Query(value = "SELECT new io.wisoft.testermatchingplatform.web.dto.response.maker.dto.SimpleReviewDTO(t.title, tr.starPoint, tr.comment) " +
            "FROM ApplyInformation a, TesterReview tr, Test t " +
            "WHERE a.id=tr.applyInformation AND a.test.id=t.id AND a.tester.id=?1 " +
            "ORDER BY tr.registerTime")
    List<SimpleReviewDTO> findByTesterId(UUID id);

    boolean existsByApplyInformation_Id(UUID uuid);

    @Query(value = "SELECT COUNT(tr.id) > 0 " +
            "FROM ApplyInformation a, TesterReview tr, Test t " +
            "WHERE tr.applyInformation.id = a.id AND a.test.id= t.id AND t.id=?1")
    boolean isTesterReview(UUID id);

}
