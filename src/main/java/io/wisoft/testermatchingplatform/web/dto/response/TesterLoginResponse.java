package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterLoginResponse {
    private final String token;
    private final UUID id;
    private final String nickname;

    public static TesterLoginResponse fromTester(Tester tester) {

        TesterLoginResponse response = new TesterLoginResponse(
                "mock Token", tester.getId(), tester.getNickname()
        );
        return response;
    }

    public static TesterLoginResponse newInstance(
            final UUID id,
            final String nickname
    ) {

        TesterLoginResponse response = new TesterLoginResponse(
                "mock Token", id, nickname
        );
        return response;
    }
}
