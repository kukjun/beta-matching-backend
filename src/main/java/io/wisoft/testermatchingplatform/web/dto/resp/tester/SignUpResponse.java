package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponse {
    private Long id;

    public static SignUpResponse from(final Long id) {
        return new SignUpResponse(
                id
        );
    }
}
