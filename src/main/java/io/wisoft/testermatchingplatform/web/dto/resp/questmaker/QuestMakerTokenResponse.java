package io.wisoft.testermatchingplatform.web.dto.resp.questmaker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestMakerTokenResponse {
    private Long id;

    private String accessToken;

    private String refreshToken;
}
