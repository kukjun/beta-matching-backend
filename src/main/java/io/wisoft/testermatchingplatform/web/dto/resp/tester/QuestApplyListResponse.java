package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import io.wisoft.testermatchingplatform.domain.apply.Apply;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestApplyListResponse {
    private String QuestName;
    private boolean isApprove;

    public static QuestApplyListResponse from(final Apply apply) {
        boolean isApprove = false;
        if(apply.getPermissionTime() != null) isApprove = true;
        return new QuestApplyListResponse(
                apply.getQuest().getTitle(),
                isApprove
        );
    }
}
