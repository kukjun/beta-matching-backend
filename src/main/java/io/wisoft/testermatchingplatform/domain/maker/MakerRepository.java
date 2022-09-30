package io.wisoft.testermatchingplatform.domain.maker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MakerRepository extends JpaRepository<Maker, UUID> {

    Optional<Maker> findByEmail(String email);



    @Query("update Maker q set q.refreshToken = ?2 where q.id = ?1")
    @Modifying
    void setRefreshToken(UUID id, String token);

    @Query("select q.refreshToken from Maker q where q.id = ?1")
    String getRefreshToken(UUID id);
}
