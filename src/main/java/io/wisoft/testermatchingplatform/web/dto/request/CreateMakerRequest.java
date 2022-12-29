package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.domain.Maker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerRequest {

    private final String email;
    private final String password;
    private final String nickname;
    private final String phoneNumber;
    private final String company;

    public Maker toMaker() {
        Maker maker = Maker.newInstance(
                getEmail(),
                getPassword(),
                getNickname(),
                getPhoneNumber(),
                getCompany()
        );
        return maker;
    }

    public static CreateMakerRequest newInstance(
            final String email,
            final String password,
            final String nickname,
            final String phoneNumber,
            final String company
    ) {
        return new CreateMakerRequest(
                email,
                password,
                nickname,
                phoneNumber,
                company
        );
    }
}
