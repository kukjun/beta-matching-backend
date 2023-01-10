package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.domain.Maker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerRequest {

    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String company;

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
        CreateMakerRequest request = new CreateMakerRequest();
        request.email = email;
        request.password = password;
        request.nickname = nickname;
        request.phoneNumber = phoneNumber;
        request.company = company;

        return request;
    }
}
