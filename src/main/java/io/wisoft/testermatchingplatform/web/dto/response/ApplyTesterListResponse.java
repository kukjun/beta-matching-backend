package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.web.dto.ApplyTesterDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyTesterListResponse {
    private final List<ApplyTesterDTO> applyTesterDTOList = new ArrayList<>();

    public static ApplyTesterListResponse fromApplyInformationList(List<ApplyInformation> applyInformationList) {
        ApplyTesterListResponse response = new ApplyTesterListResponse();
        for (ApplyInformation applyInformation : applyInformationList) {
            response.getApplyTesterDTOList().add(ApplyTesterDTO.fromApplyInformation(applyInformation));
        }

        return response;
    }


}
