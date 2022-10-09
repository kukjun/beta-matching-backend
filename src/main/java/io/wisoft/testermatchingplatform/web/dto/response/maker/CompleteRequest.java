package io.wisoft.testermatchingplatform.web.dto.response.maker;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CompleteRequest {
    List<UUID> requestCompleteDTO;

}
