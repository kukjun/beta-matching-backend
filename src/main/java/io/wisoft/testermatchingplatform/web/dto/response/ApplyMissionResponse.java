package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyMissionResponse {
    final UUID applyId;

    public static ApplyMissionResponse fromApplyInformation(ApplyInformation applyInformation) {
        ApplyMissionResponse response = new ApplyMissionResponse(applyInformation.getId());
        return response;
    }
}
