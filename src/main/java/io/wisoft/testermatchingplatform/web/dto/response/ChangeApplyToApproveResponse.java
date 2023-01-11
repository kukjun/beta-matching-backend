package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeApplyToApproveResponse {
    private final List<UUID> successApplyInformationIdList = new ArrayList<>();

    public static ChangeApplyToApproveResponse fromApplyInformationList(List<ApplyInformation> applyInformationList) {
        ChangeApplyToApproveResponse response = new ChangeApplyToApproveResponse();
        for (ApplyInformation applyInformation : applyInformationList) {
            response.getSuccessApplyInformationIdList().add(applyInformation.getId());
        }
        return response;
    }
}
