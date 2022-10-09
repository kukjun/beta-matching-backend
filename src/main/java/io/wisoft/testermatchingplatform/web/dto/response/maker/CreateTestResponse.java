package io.wisoft.testermatchingplatform.web.dto.response.maker;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTestResponse {
    private UUID id;

    public CreateTestResponse(UUID id) {
        this.id = id;
    }
}
