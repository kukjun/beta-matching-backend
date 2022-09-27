package io.wisoft.testermatchingplatform.domain.makerreview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerReviewRepository extends JpaRepository<MakerReview,String> {
}
