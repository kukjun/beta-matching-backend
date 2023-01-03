package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.TesterReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleReviewDTO {
    private final String title;
    private final int starPoint;
    private final String comment;

    public static SimpleReviewDTO fromTesterReview(TesterReview testerReview) {
        SimpleReviewDTO dto = new SimpleReviewDTO(
                testerReview.getApplyInformation().getMission().getTitle(),
                testerReview.getStarPoint(),
                testerReview.getComment()
        );

        return dto;
    }
}
