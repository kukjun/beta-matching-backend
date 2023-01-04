package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.TesterReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterReviewDTO {
    private final UUID applyInformationId;
    private final String nickname;
    private final int starPoint;
    private final String comment;
    private final String status;

    public TesterReview toTesterReview(ApplyInformation applyInformation) {
        TesterReview testerReview = TesterReview.newInstance(applyInformation, starPoint, comment);
        return testerReview;
    }

    public static TesterReviewDTO newInstance(
            final UUID applyInformationId,
            final String nickname,
            final int starPoint,
            final String comment,
            final String status
    ) {
        TesterReviewDTO dto = new TesterReviewDTO(
                applyInformationId,
                nickname,
                starPoint,
                comment,
                status
        );

        return dto;
    }
}
