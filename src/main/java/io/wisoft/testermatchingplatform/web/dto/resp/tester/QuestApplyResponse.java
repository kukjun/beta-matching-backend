package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestApplyResponse {

    private Long id;

    public static QuestApplyResponse from(Long applyId) {
        return new QuestApplyResponse(
                applyId
        );
    }

}
