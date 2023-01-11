package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyMissionResponse {
    private UUID applyId;

    public static ApplyMissionResponse fromApplyInformation(ApplyInformation applyInformation) {
        ApplyMissionResponse response = new ApplyMissionResponse();
        response.applyId = applyInformation.getId();
        return response;
    }
}
