package io.wisoft.testermatchingplatform.web.dto.request.maker;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ApproveInformationDTO {
    private UUID id;
    private boolean state;

    public ApplyInformation toEntity(ApplyInformation applyInformation) {
        applyInformation.setApproveTime(new Timestamp(new Date().getTime()));
        applyInformation.setApproveCheck(state);
        return applyInformation;
    }
}
