package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.web.dto.response.maker.dto.ApplyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindApplyResponse {
    private List<ApplyDTO> applyDTOList;
}
