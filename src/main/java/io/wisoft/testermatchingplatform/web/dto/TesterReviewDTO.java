package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.TesterReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterReviewDTO {
    private UUID id;
    private String nickname;
    private int starPoint;
    private String comment;
    private String status;

    public TesterReview toTesterReview(ApplyInformation applyInformation) {
        TesterReview testerReview = TesterReview.newInstance(applyInformation, starPoint, comment);
        return testerReview;
    }

    public static TesterReviewDTO newInstance(
            final UUID testerId,
            final String nickname,
            final int starPoint,
            final String comment,
            final String status
    ) {
        TesterReviewDTO dto = new TesterReviewDTO();
        dto.id = testerId;
        dto.nickname = nickname;
        dto.starPoint = starPoint;
        dto.comment = comment;
        dto.status = status;

        return dto;
    }
}
