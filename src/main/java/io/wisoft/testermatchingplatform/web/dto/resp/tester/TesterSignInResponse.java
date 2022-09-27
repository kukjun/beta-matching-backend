package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TesterSignInResponse {
    private Long id;
    private String accessToken;

    public TesterSignInResponse(Long id) {
        this.id = id;
    }

    public static TesterSignInResponse from(final Long id) {
        return new TesterSignInResponse(
                id
        );
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
