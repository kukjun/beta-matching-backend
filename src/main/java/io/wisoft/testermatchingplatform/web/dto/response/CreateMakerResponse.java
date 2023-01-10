package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Maker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMakerResponse {
    private UUID id;

    public static CreateMakerResponse fromMaker(Maker maker) {
        CreateMakerResponse response = new CreateMakerResponse();
        response.id = maker.getId();
        return response;

    }

    public static CreateMakerResponse newInstance(UUID makerId) {
        CreateMakerResponse response = new CreateMakerResponse();
        response.id = makerId;
        return response;
    }
}
