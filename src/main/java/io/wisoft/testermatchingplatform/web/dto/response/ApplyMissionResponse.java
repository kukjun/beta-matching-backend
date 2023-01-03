package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyMissionResponse {
    final UUID applyId;

    public static ApplyMissionResponse newInstance(UUID applyId) {
        ApplyMissionResponse response = new ApplyMissionResponse(applyId);
        return response;
    }
}
