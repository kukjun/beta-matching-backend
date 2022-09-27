package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponse {
    private Long id;
    public static SignInResponse from(final Long id){
        return new SignInResponse(
                id
        );
    }
}