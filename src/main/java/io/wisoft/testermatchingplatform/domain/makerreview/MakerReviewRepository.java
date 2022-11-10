package io.wisoft.testermatchingplatform.domain.makerreview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MakerReviewRepository extends JpaRepository<MakerReview, UUID> {

    @Query(value = "SELECT COUNT(m.id) > 0 FROM ApplyInformation a, MakerReview m WHERE m.applyInformation.id = a.id and a.id =?1")
    boolean isMakerReview(UUID id);

}
