package io.wisoft.testermatchingplatform.web.dto.request.maker;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TesterReviewDTO {
    private UUID id;
    private String nickname;
    private int starPoint;
    private String comment;
    private String status;

    public TesterReview toEntity(ApplyInformation applyInformation) {
        TesterReview testerReview = new TesterReview();
        testerReview.setApplyInformation(applyInformation);
        testerReview.setComment(comment);
        testerReview.setStarPoint(starPoint);
        return testerReview;
    }
}
