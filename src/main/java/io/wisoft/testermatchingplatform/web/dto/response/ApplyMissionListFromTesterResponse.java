package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.ApplyInformationStatus;
import io.wisoft.testermatchingplatform.web.dto.AppliedMissionDTO;
import io.wisoft.testermatchingplatform.web.dto.ApprovedMissionDTO;
import io.wisoft.testermatchingplatform.web.dto.ExecutedMissionDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyMissionListFromTesterResponse {
    private final List<AppliedMissionDTO> appliedMissionDTOList = new ArrayList<>();
    private final List<ApprovedMissionDTO> approvedMissionDTOList = new ArrayList<>();
    private final List<ExecutedMissionDTO> executedMissionDTOList = new ArrayList<>();

    public static ApplyMissionListFromTesterResponse fromApplyInformationList(
            List<ApplyInformation> applyInformationList
    ) {
        ApplyMissionListFromTesterResponse response = new ApplyMissionListFromTesterResponse();

        for (ApplyInformation applyInformation : applyInformationList) {
            ApplyInformationStatus status = applyInformation.getStatus();
            switch (status) {
                case APPLY:
                    response.appliedMissionDTOList.add(AppliedMissionDTO.fromApplyInformation(applyInformation));
                    break;
                case APPROVE_SUCCESS:
                    response.approvedMissionDTOList.add(ApprovedMissionDTO.fromApplyInformation(applyInformation));
                    break;
                case EXECUTE_SUCCESS:
                case EXECUTE_FAIL:
                    response.executedMissionDTOList.add(ExecutedMissionDTO.fromApplyInformation(applyInformation));
            }
        }

        return response;
    }
}
