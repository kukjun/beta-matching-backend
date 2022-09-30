package io.wisoft.testermatchingplatform.domain.testerreview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TesterReviewRepository extends JpaRepository<TesterReview, UUID> {

}
