package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.web.dto.response.maker.dto.ApplyPeriodTestDTO;
import io.wisoft.testermatchingplatform.web.dto.response.maker.dto.ApprovePeriodTestDTO;
import io.wisoft.testermatchingplatform.web.dto.response.maker.dto.CompletePeriodTestDTO;
import io.wisoft.testermatchingplatform.web.dto.response.maker.dto.ProgressPeriodTestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class findTestsResponse {
    private List<ApplyPeriodTestDTO> applyPeriodTestList;
    private List<ApprovePeriodTestDTO> approvePeriodTestList;
    private List<ProgressPeriodTestDTO> progressPeriodTestList;
    private List<CompletePeriodTestDTO> completePeriodTestList;

}
