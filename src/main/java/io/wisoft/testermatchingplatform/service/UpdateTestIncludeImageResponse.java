package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTestIncludeImageResponse {
    private final UUID id;


    public static UpdateTestIncludeImageResponse fromTest(Tests test) {
        UpdateTestIncludeImageResponse response = new UpdateTestIncludeImageResponse(test.getId());
        return response;
    }
}
