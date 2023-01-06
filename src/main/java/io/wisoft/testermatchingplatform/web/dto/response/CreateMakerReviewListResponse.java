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

    public static CreateMakerReviewListResponse fromMakerReviewId(UUID makerReviewId) {
        CreateMakerReviewListResponse response = new CreateMakerReviewListResponse(makerReviewId);
        return response;
    }
}
