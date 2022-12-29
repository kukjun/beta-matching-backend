package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MakerLoginRequest {

    private final String email;
    private final String password;

    public static MakerLoginRequest newInstance(
            final String email,
            final String password
    ) {
        MakerLoginRequest request = new MakerLoginRequest(
                email, password
        );
        return request;
    }
}
