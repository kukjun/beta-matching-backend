package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.web.dto.SimpleMissionDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Top4MissionListResponse {
    private final List<SimpleMissionDTO> simpleMissionDTOList = new ArrayList<>();

    public static Top4MissionListResponse toMissionList(List<Mission> missionList) {
        Top4MissionListResponse response = new Top4MissionListResponse();
        for (Mission mission : missionList) {
            response.getSimpleMissionDTOList().add(SimpleMissionDTO.fromMission(mission));
        }

        return response;
    }
}
