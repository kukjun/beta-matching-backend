package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyMissionRequest {
    private UUID missionId;

    public static ApplyMissionRequest newInstance(final UUID missionId) {
        ApplyMissionRequest request = new ApplyMissionRequest();
        request.missionId = missionId;
        return request;
    }
}
