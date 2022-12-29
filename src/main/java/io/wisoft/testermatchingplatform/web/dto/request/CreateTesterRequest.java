package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTesterRequest {

    private final String email;
    private final String password;
    private final String nickname;
    private final String phoneNumber;
    private final String introMessage;

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
        return new CreateTesterRequest(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        );
    }
}
