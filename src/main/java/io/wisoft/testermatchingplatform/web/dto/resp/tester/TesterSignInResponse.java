package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.Getter;

@Getter
public class TesterSignInResponse {
    private Long id;
    private String email;
    private String nickname;

    private String token;

    public TesterSignInResponse(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
