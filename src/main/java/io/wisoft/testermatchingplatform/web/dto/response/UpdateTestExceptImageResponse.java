package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTestExceptImageResponse {
    private final UUID id;

    public static UpdateTestExceptImageResponse fromTest(Tests test) {
        UpdateTestExceptImageResponse response = new UpdateTestExceptImageResponse(test.getId());
        return response;
    }
}
