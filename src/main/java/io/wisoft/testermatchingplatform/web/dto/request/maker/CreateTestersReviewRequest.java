package io.wisoft.testermatchingplatform.web.dto.request.maker;

import io.wisoft.testermatchingplatform.web.dto.request.maker.dto.TesterReviewDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTestersReviewRequest {
    private List<TesterReviewDTO> testerReviewDTOList;

}
