package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyMissionRequest {
    private final UUID missionId;

    public static ApplyMissionRequest newInstance(final UUID missionId) {
        ApplyMissionRequest request = new ApplyMissionRequest(missionId);
        return request;
    }
}
