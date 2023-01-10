package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterLoginRequest {

    private String email;
    private String password;

    public static TesterLoginRequest newInstance(
            final String email,
            final String password
    ) {
        TesterLoginRequest request = new TesterLoginRequest();
        request.email = email;
        request.password = password;
        return request;
    }

}
