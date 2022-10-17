package io.wisoft.testermatchingplatform.web.dto.request.tester;


import io.wisoft.testermatchingplatform.anotation.Auth;
import io.wisoft.testermatchingplatform.domain.maker.Maker;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.web.dto.request.MakerRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TesterRegisterRequest {

    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String introMessage;


    public static Tester toEntity(TesterRegisterRequest request) {
        Tester tester = new Tester();
        tester.setEmail(request.email);
        tester.setPassword(request.password);
        tester.setNickname(request.nickname);
        tester.setPhoneNumber(request.phoneNumber);
        tester.setIntroMessage(request.introMessage);
        tester.setPoint(0L);

        return tester;
    }
}
