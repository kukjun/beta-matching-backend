package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTesterRequest {

    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String introMessage;

    public Tester toTester() {
        Tester tester = Tester.newInstance(
                getEmail(),
                getPassword(),
                getNickname(),
                getPhoneNumber(),
                getIntroMessage()
        );
        return tester;
    }

    public static CreateTesterRequest newInstance(
            final String email,
            final String password,
            final String nickname,
            final String phoneNumber,
            final String introMessage
    ) {
        CreateTesterRequest request = new CreateTesterRequest();
        request.email = email;
        request.password = password;
        request.nickname = nickname;
        request.phoneNumber = phoneNumber;
        request.introMessage = introMessage;
        return request;
    }
}
