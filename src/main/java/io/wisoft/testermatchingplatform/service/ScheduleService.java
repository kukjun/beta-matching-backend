package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

@Transactional(readOnly = true)
public class ScheduleService {
    MissionRepository missionRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * *", zone = "Asia/Seoul")
    public void updateTest() {
        List<Mission> missionList = missionRepository.findAll();

        for (Mission mission : missionList) {
            mission.refreshMissionStatus();
        }
    }
}
