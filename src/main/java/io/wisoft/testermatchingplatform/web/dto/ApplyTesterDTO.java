package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.TesterReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyTesterDTO {

    private final UUID id;
    private final String nickname;
    private final String status;
    private final List<SimpleReviewDTO> beforeMissions = new ArrayList<>();

    public static ApplyTesterDTO fromApplyInformation(ApplyInformation applyInformation) {
        ApplyTesterDTO dto = new ApplyTesterDTO(
                applyInformation.getTester().getId(),
                applyInformation.getTester().getNickname(),
                applyInformation.getStatus().toString()
        );

        List<ApplyInformation> applyInformationList = applyInformation.getTester().getApplyInformationList();
        for (ApplyInformation testerApplyInformation : applyInformationList) {
            TesterReview review = testerApplyInformation.getTesterReview();
            if (review != null) {
                dto.getBeforeMissions().add(SimpleReviewDTO.fromTesterReview(review));
            }
        }
        return dto;
    }
}
