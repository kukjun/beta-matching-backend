package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MakerLoginRequest {

    private String email;
    private String password;

    public static MakerLoginRequest newInstance(
            final String email,
            final String password
    ) {
        MakerLoginRequest request = new MakerLoginRequest();
        request.email = email;
        request.password = password;

        return request;
    }
}
