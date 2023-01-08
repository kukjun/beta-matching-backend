package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.MakerReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerReviewListResponse {
    private final UUID makerReviewId;

    public static CreateMakerReviewListResponse fromMakerReview(MakerReview makerReview) {
        CreateMakerReviewListResponse response = new CreateMakerReviewListResponse(makerReview.getId());
        return response;
    }
}
