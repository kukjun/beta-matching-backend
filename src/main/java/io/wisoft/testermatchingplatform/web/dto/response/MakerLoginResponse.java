package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Maker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MakerLoginResponse {
    private final String token;
    private final UUID id;
    private final String nickname;

    public static MakerLoginResponse fromMaker(Maker maker) {
        MakerLoginResponse response = new MakerLoginResponse(
                "mock Token", maker.getId(), maker.getNickname()
        );
        return response;
    }
}
