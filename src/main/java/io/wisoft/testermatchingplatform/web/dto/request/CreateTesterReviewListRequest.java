package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.web.dto.TesterReviewDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTesterReviewListRequest {
    private List<TesterReviewDTO> testerReviewDTOList;

    public static CreateTesterReviewListRequest newInstance(List<TesterReviewDTO> dtoList) {
        CreateTesterReviewListRequest request = new CreateTesterReviewListRequest();
        request.testerReviewDTOList = dtoList;
        return request;
    }
}
