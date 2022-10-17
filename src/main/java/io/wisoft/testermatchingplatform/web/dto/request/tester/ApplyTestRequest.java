package io.wisoft.testermatchingplatform.web.dto.request.tester;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.UUID;

@Getter

public class ApplyTestRequest {
    private String testId;
}
