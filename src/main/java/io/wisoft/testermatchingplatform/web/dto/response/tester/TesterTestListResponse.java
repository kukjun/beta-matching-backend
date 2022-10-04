package io.wisoft.testermatchingplatform.web.dto.response.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TesterTestListResponse {
    List<ApplyTestListResponse> applyTestListResponseList;
    List<ApproveTestListResponse> approveTestListResponseList;
    List<QuitTestListResponse> quitTestListResponseList;
}
