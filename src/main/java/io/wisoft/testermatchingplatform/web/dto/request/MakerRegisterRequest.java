package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.domain.maker.Maker;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakerRegisterRequest {
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String company;

    public MakerRegisterRequest(String email, String password, String nickname, String phoneNumber, String company) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.company = company;
    }
    public MakerRegisterRequest() {

    }

    public static Maker toEntity(MakerRegisterRequest request) {
        Maker maker = new Maker();
        maker.setEmail(request.email);
        maker.setPassword(request.password);
        maker.setNickname(request.nickname);
        maker.setPhoneNumber(request.phoneNumber);
        maker.setCompany(request.company);
        maker.setPoint(0L);

        return maker;
    }
}
