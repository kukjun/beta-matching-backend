package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterLoginRequest {

    private final String email;
    private final String password;

    public static TesterLoginRequest newInstance(
            final String email,
            final String password
    ) {
        TesterLoginRequest request = new TesterLoginRequest(
                email,
                password
        );
        return request;
    }

}
