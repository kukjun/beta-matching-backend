package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.MakerReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerReviewResponse {
    private final UUID makerReviewId;

    public static CreateMakerReviewResponse fromMakerReview(MakerReview makerReview) {
        CreateMakerReviewResponse response = new CreateMakerReviewResponse(makerReview.getId());
        return response;
    }
}
