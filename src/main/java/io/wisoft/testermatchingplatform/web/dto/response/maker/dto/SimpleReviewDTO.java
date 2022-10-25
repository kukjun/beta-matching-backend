package io.wisoft.testermatchingplatform.web.dto.response.maker.dto;


import io.wisoft.testermatchingplatform.domain.testerreview.TesterReview;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleReviewDTO {
    private String title;
    private int starPoint;
    private String comment;
}
