package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.MakerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MakerReviewRepository extends JpaRepository<MakerReview, UUID> {

    @Override
    Optional<MakerReview> findById(UUID uuid);

    @Override
    List<MakerReview> findAll();

}
