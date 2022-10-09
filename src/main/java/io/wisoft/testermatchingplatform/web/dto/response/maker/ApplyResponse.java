package io.wisoft.testermatchingplatform.web.dto.response.maker;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApplyResponse {
    private List<ApplyDTO> applyDTOList;
}
