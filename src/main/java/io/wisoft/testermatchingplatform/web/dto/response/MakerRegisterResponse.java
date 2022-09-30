package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MakerRegisterResponse {
    private UUID id;

    public MakerRegisterResponse(UUID id) {
        this.id = id;
    }
}
