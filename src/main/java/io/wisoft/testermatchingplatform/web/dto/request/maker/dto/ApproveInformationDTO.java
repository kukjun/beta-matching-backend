package io.wisoft.testermatchingplatform.web.dto.request.maker.dto;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ApproveInformationDTO implements Serializable {
    private String id;

    public ApplyInformation toEntity(ApplyInformation applyInformation) {
        applyInformation.setApproveTime(new Timestamp(new Date().getTime()));
        applyInformation.setApproveCheck(true);
        return applyInformation;
    }
}
