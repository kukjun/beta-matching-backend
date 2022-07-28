package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.Getter;

@Getter
public class QuestApplyResponse {

    private Long id;

    public QuestApplyResponse(Long applyId) {
        id = applyId;
    }

}
