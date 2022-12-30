package io.wisoft.testermatchingplatform.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyTestListFromTesterResponse {
    private final List<AppliedTestDTO> appliedTestDTOList;
    private final List<ApprovedTestDTO> approvedTestDTOList;
    private final List<CompletedTestDTO> completedTestDTOList;

    public static ApplyTestListFromTesterResponse fromDTO(
            final List<AppliedTestDTO> appliedTestDTOList,
            final List<ApprovedTestDTO> approvedTestDTOList,
            final List<CompletedTestDTO> completedTestDTOList
    ) {
        ApplyTestListFromTesterResponse response = new ApplyTestListFromTesterResponse(
                appliedTestDTOList,
                approvedTestDTOList,
                completedTestDTOList
        );

        return response;
    }
}
