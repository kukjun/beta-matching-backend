package io.wisoft.testermatchingplatform.web.dto.response.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ApplyInformationIdResponse {
    private UUID id;
}
