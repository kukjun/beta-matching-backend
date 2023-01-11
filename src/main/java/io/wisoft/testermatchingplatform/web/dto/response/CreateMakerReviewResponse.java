package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.MakerReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerReviewResponse {
    private UUID makerReviewId;

    public static CreateMakerReviewResponse fromMakerReview(MakerReview makerReview) {
        CreateMakerReviewResponse response = new CreateMakerReviewResponse();
        response.makerReviewId = makerReview.getId();
        return response;
    }
}
