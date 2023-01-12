package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Maker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MakerLoginResponse {
    private String token;
    private UUID id;
    private String nickname;

    public static MakerLoginResponse fromMaker(Maker maker) {
        MakerLoginResponse response = new MakerLoginResponse();
        response.token = "mock Token";
        response.id = maker.getId();
        response.nickname = maker.getNickname();
        return response;
    }

    public static MakerLoginResponse newInstance(
        final String token,
        final UUID id,
        final String nickname
    ) {
        MakerLoginResponse response = new MakerLoginResponse();
        response.token = "mock Token";
        response.id = id;
        response.nickname = nickname;
        return response;
    }
}
