package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.web.dto.response.maker.dto.CompleteTesterDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindCompleteTesterListResponse {
    List<CompleteTesterDTO> completeTesterDTOList;

}
