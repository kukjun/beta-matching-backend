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
    private String comment;
    private int starPoint;


    public static CreateMakerReviewRequest newInstance(int starPoint, String testComment) {
        CreateMakerReviewRequest request = new CreateMakerReviewRequest();
        request.starPoint = starPoint;
        request.comment = testComment;
        return request;
    }

    public MakerReview toMakerReview(ApplyInformation applyInformation) {
        System.out.println("getStarPoint = " + getStarPoint());
        System.out.println("getComment() = " + getComment());
        MakerReview makerReview = MakerReview.newInstance(applyInformation, getStarPoint(), getComment());
        return makerReview;
    }


}
