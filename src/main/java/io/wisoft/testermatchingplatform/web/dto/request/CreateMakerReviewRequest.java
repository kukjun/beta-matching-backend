package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.MakerReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerReviewRequest {
    private int starPoint;
    private String comment;

    public static CreateMakerReviewRequest newInstance(UUID applyInformationId, int starPoint, String testComment) {
        CreateMakerReviewRequest request = new CreateMakerReviewRequest();
        request.starPoint = starPoint;
        request.comment = testComment;

        return request;
    }

    public MakerReview toMakerReview(ApplyInformation applyInformation) {
        MakerReview makerReview = MakerReview.newInstance(applyInformation, getStarPoint(), getComment());
        return makerReview;
    }


}
