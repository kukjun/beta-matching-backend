package io.wisoft.testermatchingplatform.domain.tester;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TesterRepository extends JpaRepository<Tester, UUID> {

    public Optional<Tester> findByEmail(String email);

    public boolean existsByEmailAndPassword(String email, String password);

    public boolean existsByEmail(String email);

    public boolean existsByNickname(String nickname);

    public boolean existsByPhoneNumber(String phoneNumber);

    @Query("update Maker q set q.refreshToken = ?2 where q.id = ?1")
    @Modifying
    void setRefreshToken(Long id, String token);

    @Query("select q.refreshToken from Maker q where q.id = ?1")
    String getRefreshToken(Long id);

}
