package io.wisoft.testermatchingplatform.web.dto.response.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TesterTestListResponse {
    List<ApplyTestDTO> applyTestListResponseList;
    List<ApproveTestDTO> approveTestListResponseList;
    List<QuitTestDTO> quitTestListResponseList;
}
