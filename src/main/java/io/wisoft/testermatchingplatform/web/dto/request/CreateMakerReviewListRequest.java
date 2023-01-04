package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.MakerReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerReviewListRequest {
    private final UUID applyInformationId;
    private final int starPoint;
    private final String comment;

    public MakerReview toMakerReview(ApplyInformation applyInformation) {
        MakerReview makerReview = MakerReview.newInstance(applyInformation, getStarPoint(), getComment());
        return makerReview;
    }

}
