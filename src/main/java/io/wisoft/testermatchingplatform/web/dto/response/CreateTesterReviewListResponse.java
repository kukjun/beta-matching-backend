package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTesterReviewListResponse {

    private final List<UUID> testerReviewIdDTOList;

    public static CreateTesterReviewListResponse fromUUIDList(List<UUID> dtoList) {
        CreateTesterReviewListResponse response = new CreateTesterReviewListResponse(dtoList);
        return response;
    }
}
