package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import io.wisoft.testermatchingplatform.domain.tester.Tester;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetailTesterResponse {

    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String preferCategoryName;
    private String introMessage;
    private String introPictureRef;
    public static DetailTesterResponse from(final  Tester tester){
        return new DetailTesterResponse(
                tester.getEmail(),
                tester.getPassword(),
                tester.getNickname(),
                tester.getPhoneNumber(),
                tester.getPreferCategory().getName(),
                tester.getIntroMessage(),
                tester.getIntroPictureRef()
        );
    }


}
