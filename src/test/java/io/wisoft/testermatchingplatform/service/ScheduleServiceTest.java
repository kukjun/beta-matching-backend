package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.domain.MissionDate;
import io.wisoft.testermatchingplatform.repository.MissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private MissionRepository missionRepository;

    private ScheduleService scheduleService;

    @BeforeEach
    public void prepareTest() {
        scheduleService = new ScheduleService(missionRepository);
    }

    // Enum 로직이다. -> Domain 책임의 로직이다. -> Service 테스트 영역이 아니다.
    @Test
    @DisplayName("Mission의 상태를 하루마다 초기화해주는 Service 테스트 - 성공")
    public void updateMissionStateSuccessTest() throws Exception {
        //given
        List<Mission> missionList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Mission mockMission = mock(Mission.class);
            missionList.add(mockMission);
        }

        when(missionRepository.findAll()).thenReturn(missionList);
        //when
        //then
        scheduleService.updateMissionState();

    }


}