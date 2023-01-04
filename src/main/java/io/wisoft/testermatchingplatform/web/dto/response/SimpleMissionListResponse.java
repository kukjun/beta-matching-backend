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
public class SimpleMissionListResponse {
    private final List<SimpleMissionDTO> dtoList = new ArrayList<>();

    public static SimpleMissionListResponse fromMissionList(List<Mission> list) {

        SimpleMissionListResponse response = new SimpleMissionListResponse();
        for (Mission mission : list) {
            SimpleMissionDTO dto = SimpleMissionDTO.fromMission(mission);
            response.dtoList.add(dto);
        }
        return response;
    }
}