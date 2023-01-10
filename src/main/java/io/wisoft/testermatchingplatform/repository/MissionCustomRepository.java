package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Mission;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MissionCustomRepository {
    List<Mission> findDeadlineTop4Mission(@Param("currentDate") LocalDate currentDate);
    List<Mission> findPopularTop4Mission(@Param("currentDate") LocalDate currentDate);
}
