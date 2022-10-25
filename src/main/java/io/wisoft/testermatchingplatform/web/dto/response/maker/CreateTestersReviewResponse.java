package io.wisoft.testermatchingplatform.web.dto.response.maker;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateTestersReviewResponse {
    List<UUID> testerReviewUUID;
}
