package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Mission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMissionResponse {
    private final UUID id;

    public static CreateMissionResponse fromMission(Mission mission) {
        CreateMissionResponse response = new CreateMissionResponse(mission.getId());
        return response;
    }
}
