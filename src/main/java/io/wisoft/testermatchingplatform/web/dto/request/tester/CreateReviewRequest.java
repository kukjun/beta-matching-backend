package io.wisoft.testermatchingplatform.web.dto.request.tester;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
public class CreateReviewRequest {

    private int starPoint;
    private String comment;
}
