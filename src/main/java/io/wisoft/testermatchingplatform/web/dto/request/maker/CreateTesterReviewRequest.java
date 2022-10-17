package io.wisoft.testermatchingplatform.web.dto.request.maker;

import io.wisoft.testermatchingplatform.domain.testerreview.TesterReview;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTesterReviewRequest {
    private List<TesterReviewDTO> testerReviewDTOList;



}
