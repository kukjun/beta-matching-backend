package io.wisoft.testermatchingplatform.web.dto.request.maker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ConfirmApplyRequest {
    List<ApproveInformationDTO> approveInformationDTOList;
}
