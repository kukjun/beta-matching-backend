package io.wisoft.testermatchingplatform.web.dto.resp.questmaker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestMakerSignInResponse {

    private Long id;

    public QuestMakerSignInResponse(Long id) {
        this.id = id;
    }

    public static QuestMakerSignInResponse from(final Long id) {
        return new QuestMakerSignInResponse(id);
    }
}
