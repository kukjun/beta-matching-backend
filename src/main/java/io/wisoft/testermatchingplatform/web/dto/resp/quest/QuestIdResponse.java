package io.wisoft.testermatchingplatform.web.dto.resp.quest;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestIdResponse {

    private Long id;

    public static QuestIdResponse from(final Test test) {
        return new QuestIdResponse(test.getId());
    }
}
