package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.ApplyInformationStatus;
import io.wisoft.testermatchingplatform.web.dto.AppliedTestDTO;
import io.wisoft.testermatchingplatform.web.dto.ApprovedTestDTO;
import io.wisoft.testermatchingplatform.web.dto.ExecutedTestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyTestListFromTesterResponse {
    private final List<AppliedTestDTO> appliedTestDTOList = new ArrayList<>();
    private final List<ApprovedTestDTO> approvedTestDTOList = new ArrayList<>();
    private final List<ExecutedTestDTO> executedTestDTOList = new ArrayList<>();

    public static ApplyTestListFromTesterResponse fromApplyInformationList(
            List<ApplyInformation> applyInformationList
    ) {
        ApplyTestListFromTesterResponse response = new ApplyTestListFromTesterResponse();

        for (ApplyInformation applyInformation : applyInformationList) {
            ApplyInformationStatus status = applyInformation.getStatus();
            switch (status) {
                case APPLY:
                    response.appliedTestDTOList.add(AppliedTestDTO.fromApplyInformation(applyInformation));
                    break;
                case APPROVE_SUCCESS:
                    response.approvedTestDTOList.add(ApprovedTestDTO.fromApplyInformation(applyInformation));
                    break;
                case EXECUTE_SUCCESS:
                case EXECUTE_FAIL:
                    response.executedTestDTOList.add(ExecutedTestDTO.fromApplyInformation(applyInformation));
            }
        }

        return response;
    }
}
