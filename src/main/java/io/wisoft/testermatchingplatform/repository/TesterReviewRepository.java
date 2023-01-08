package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.TesterReview;
import io.wisoft.testermatchingplatform.handler.exception.service.TesterReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TesterReviewRepository extends JpaRepository<TesterReview, UUID> {
    @Override
    Optional<TesterReview> findById(UUID uuid);

    @Override
    List<TesterReview> findAll();
}
