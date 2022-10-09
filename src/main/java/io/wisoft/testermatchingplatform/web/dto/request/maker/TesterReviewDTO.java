package io.wisoft.testermatchingplatform.web.dto.request.maker;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TesterReviewDTO {
    private UUID applyInformationId;
    private int starPoint;
    private String comment;

    public TesterReview toEntity(ApplyInformation applyInformation) {
        TesterReview testerReview = new TesterReview();
        testerReview.setComment(comment);
        testerReview.setApplyInformation(applyInformation);
        testerReview.setStarPoint(starPoint);
        return testerReview;
    }
}
