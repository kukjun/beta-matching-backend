package io.wisoft.testermatchingplatform.web.controller.maker;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateTesterReviewResponse {
    List<UUID> testerReviewUUID;
}
