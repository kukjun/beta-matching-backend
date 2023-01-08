package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.handler.exception.service.MakerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MakerRepository extends JpaRepository<Maker, UUID> {

    @Override
    Optional<Maker> findById(UUID uuid);

    @Query("select m from Maker m where m.email = :email")
    Maker findByEmail(@Param("email") String email);

    @Override
    List<Maker> findAll();

    @Override
    long count();


}
