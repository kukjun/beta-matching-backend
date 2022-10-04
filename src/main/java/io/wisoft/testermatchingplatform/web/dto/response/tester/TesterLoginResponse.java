package io.wisoft.testermatchingplatform.web.dto.response.tester;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TesterLoginResponse {

    private String token;
    private UUID id;
    private String nickname;

    public TesterLoginResponse(UUID id, String nickname) {
        this.token = "mockToken";
        this.id = id;
        this.nickname = nickname;
    }
}
