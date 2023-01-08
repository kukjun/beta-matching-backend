package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.handler.exception.service.TesterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TesterRepository extends JpaRepository<Tester, UUID> {
    @Override
    Optional<Tester> findById(UUID uuid);

    @Query("select t from Tester t where t.email=:email")
    Optional<Tester> findByEmail(@Param("email") String email);

    @Override
    List<Tester> findAll();

    @Override
    long count();

}
