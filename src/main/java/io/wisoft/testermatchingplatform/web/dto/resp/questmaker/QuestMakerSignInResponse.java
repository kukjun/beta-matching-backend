package io.wisoft.testermatchingplatform.web.dto.resp.questmaker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestMakerSignInResponse {

    private Long id;
    private String nickname;

    public static QuestMakerSignInResponse from(final Long id, final String nickname) {
        return new QuestMakerSignInResponse(id, nickname);
    }
}
