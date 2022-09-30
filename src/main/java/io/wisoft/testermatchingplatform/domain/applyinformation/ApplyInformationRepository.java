package io.wisoft.testermatchingplatform.domain.applyinformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplyInformationRepository extends JpaRepository<ApplyInformation, UUID> {
}
