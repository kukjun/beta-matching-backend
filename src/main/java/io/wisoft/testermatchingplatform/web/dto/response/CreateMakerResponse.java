package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Maker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerResponse {
    private final UUID id;

    public static CreateMakerResponse fromMaker(Maker maker) {
        return new CreateMakerResponse(
                maker.getId()
        );
    }
}
