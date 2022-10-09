package io.wisoft.testermatchingplatform.web.dto.request.maker;

import io.wisoft.testermatchingplatform.domain.testerreview.TesterReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateTesterReviewRequest {
    private List<TesterReviewDTO> testerReviewDTOList;



}
