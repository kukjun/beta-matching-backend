package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.MissionStatus;
import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.web.dto.ApplyPeriodTestDTO;
import io.wisoft.testermatchingplatform.web.dto.ApprovePeriodTestDTO;
import io.wisoft.testermatchingplatform.web.dto.CompletePeriodTestDTO;
import io.wisoft.testermatchingplatform.web.dto.ProgressPeriodTestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyMissionListFromMakerResponse {
    private final List<ApplyPeriodTestDTO> applyPeriodTestDTOList = new ArrayList<>();
    private final List<ApprovePeriodTestDTO> approvePeriodTestDTOList = new ArrayList<>();
    private final List<ProgressPeriodTestDTO> progressPeriodTestDTOList = new ArrayList<>();
    private final List<CompletePeriodTestDTO> completePeriodTestDTOList = new ArrayList<>();


    public static ApplyMissionListFromMakerResponse fromMissionList(List<Mission> list) {

        ApplyMissionListFromMakerResponse response = new ApplyMissionListFromMakerResponse();

        for (Mission mission : list) {
            MissionStatus status = mission.getStatus();
            switch (status) {
                case BEFORE_APPLY:
                case APPLY:
                    response.applyPeriodTestDTOList.add(ApplyPeriodTestDTO.fromMission(mission));
                    break;
                case APPROVE:
                    response.approvePeriodTestDTOList.add(ApprovePeriodTestDTO.fromMission(mission));
                    break;
                case PROGRESS:
                    response.progressPeriodTestDTOList.add(ProgressPeriodTestDTO.fromMission(mission));
                    break;
                case COMPLETE:
                    response.completePeriodTestDTOList.add(CompletePeriodTestDTO.fromMission(mission));
                    break;
            }
        }

        return response;
    }
}
