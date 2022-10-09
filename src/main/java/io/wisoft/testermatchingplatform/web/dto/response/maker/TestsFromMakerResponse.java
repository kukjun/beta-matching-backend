package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestsFromMakerResponse {
    private List<ApplyPeriodTestDTO> applyPeriodTestList;
    private List<ApprovePeriodTestDTO> approvePeriodTestList;
    private List<ProgressPeriodTestDTO> progressPeriodTestList;
    private List<CompletePeriodTestDTO> completePeriodTestList;

}
