package io.wisoft.testermatchingplatform.domain.applyinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplyInformationRepository extends JpaRepository<ApplyInformation, UUID> {

    @Query(value = "select a.test.id from ApplyInformation a group by a.test.id order by count(a.test.id)")
    List<UUID> getTop4Test();

    int countByTestId(UUID id);
}
