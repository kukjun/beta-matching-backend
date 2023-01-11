package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTesterReviewListResponse {

    private List<UUID> testerReviewIdDTOList;

    public static CreateTesterReviewListResponse fromTesterReviewIdList(List<UUID> dtoList) {
        CreateTesterReviewListResponse response = new CreateTesterReviewListResponse();
        response.testerReviewIdDTOList = dtoList;
        return response;
    }
}
