package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.ApplyInformationStatus;
import io.wisoft.testermatchingplatform.domain.MissionStatus;
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
            MissionStatus missionStatus = applyInformation.getMission().getStatus();
            ApplyInformationStatus applyStatus = applyInformation.getStatus();
            switch (missionStatus) {
                case APPLY:
                    response.appliedMissionDTOList.add(AppliedMissionDTO.fromApplyInformation(applyInformation));
                    break;
                case APPROVE:
                case PROGRESS:
                    if(applyStatus == ApplyInformationStatus.APPROVE_SUCCESS ||
                            applyStatus == ApplyInformationStatus.EXECUTE_SUCCESS ||
                            applyStatus == ApplyInformationStatus.EXECUTE_FAIL
                    ) {
                        response.approvedMissionDTOList.add(ApprovedMissionDTO.fromApplyInformation(applyInformation));
                    }
                    break;
                case COMPLETE:
                    if (applyStatus == ApplyInformationStatus.EXECUTE_SUCCESS ||
                            applyStatus == ApplyInformationStatus.EXECUTE_FAIL
                    ) {
                        response.executedMissionDTOList.add(ExecutedMissionDTO.fromApplyInformation(applyInformation));
                    }
            }
        }

        return response;
    }
}
