package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.handler.exception.service.ApplyInformationNotDeleteException;
import io.wisoft.testermatchingplatform.handler.exception.service.ApplyInformationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplyInformationRepository extends JpaRepository<ApplyInformation, UUID> {
    @Override
    Optional<ApplyInformation> findById(UUID uuid);

    @Override
    List<ApplyInformation> findAll();

    @Query("select a from ApplyInformation a where a.tester.id=:testerId")
    List<ApplyInformation> findByTesterId(@Param("testerId") UUID testerId);

    @Override
    void deleteById(UUID applyInformationId);

}
