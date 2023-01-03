package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.web.dto.PerformTesterDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformTesterListResponse {
    private final List<PerformTesterDTO> performTesterDTOList = new ArrayList<>();

    public static PerformTesterListResponse fromApplyInformationList(List<ApplyInformation> applyInformationList) {
        PerformTesterListResponse response = new PerformTesterListResponse();
        for (ApplyInformation applyInformation : applyInformationList) {
            response.getPerformTesterDTOList().add(PerformTesterDTO.fromApplyInformation(applyInformation));
        }

        return response;
    }
}
