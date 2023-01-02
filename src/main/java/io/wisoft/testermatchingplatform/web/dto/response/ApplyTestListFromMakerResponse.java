package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.TestStatus;
import io.wisoft.testermatchingplatform.domain.Tests;
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
public class ApplyTestListFromMakerResponse {
    private final List<ApplyPeriodTestDTO> applyPeriodTestDTOList = new ArrayList<>();
    private final List<ApprovePeriodTestDTO> approvePeriodTestDTOList = new ArrayList<>();
    private final List<ProgressPeriodTestDTO> progressPeriodTestDTOList = new ArrayList<>();
    private final List<CompletePeriodTestDTO> completePeriodTestDTOList = new ArrayList<>();


    public static ApplyTestListFromMakerResponse fromTestList(List<Tests> list) {

        ApplyTestListFromMakerResponse response = new ApplyTestListFromMakerResponse();

        for (Tests test : list) {
            TestStatus status = test.getStatus();
            switch (status) {
                case BEFORE_APPLY:
                case APPLY:
                    response.applyPeriodTestDTOList.add(ApplyPeriodTestDTO.fromTest(test));
                    break;
                case APPROVE:
                    response.approvePeriodTestDTOList.add(ApprovePeriodTestDTO.fromTest(test));
                    break;
                case PROGRESS:
                    response.progressPeriodTestDTOList.add(ProgressPeriodTestDTO.fromTest(test));
                    break;
                case COMPLETE:
                    response.completePeriodTestDTOList.add(CompletePeriodTestDTO.fromTest(test));
                    break;
            }
        }

        return response;
    }
}
