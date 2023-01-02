package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Mission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMissionExceptImageResponse {
    private final UUID id;

    public static UpdateMissionExceptImageResponse fromMission(Mission test) {
        UpdateMissionExceptImageResponse response = new UpdateMissionExceptImageResponse(test.getId());
        return response;
    }
}
