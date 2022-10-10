package io.wisoft.testermatchingplatform.web.dto.response.maker;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MakerLoginResponse {
    private String token;
    private UUID id;
    private String nickname;

//    MockObject
    public MakerLoginResponse(UUID id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.token = "mockToken";
    }
}
