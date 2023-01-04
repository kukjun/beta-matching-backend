package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTesterResponse {
    private final UUID id;

    public static CreateTesterResponse fromTesterId(UUID testerId) {
        CreateTesterResponse response = new CreateTesterResponse(testerId);
        return response;
    }
}
