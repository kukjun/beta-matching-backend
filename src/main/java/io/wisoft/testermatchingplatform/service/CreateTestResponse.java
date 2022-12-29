package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTestResponse {
    private final UUID id;

    public static CreateTestResponse fromTest(Tests test) {
        CreateTestResponse response = new CreateTestResponse(test.getId());
        return response;
    }
}
