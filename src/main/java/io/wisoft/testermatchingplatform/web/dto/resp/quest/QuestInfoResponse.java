package io.wisoft.testermatchingplatform.web.dto.resp.quest;

import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.web.dto.resp.task.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class QuestInfoResponse {

    private String title;
    private String content;
    private String categoryName;
    private Timestamp registerTime;
    private Timestamp recruitmentTimeStart;
    private Timestamp recruitmentTimeLimit;
    private Timestamp durationTimeStart;
    private Timestamp durationTimeLimit;
    private Timestamp modifyTimeStart;
    private Timestamp modifyTimeLimit;
    private String questMakerName;
    private int participantCapacity;
    private int reward;
    private String requireCondition;
    private String preferenceCondition;
    private List<TaskResponse> taskList;
    public static QuestInfoResponse from(final Test test, final List<TaskResponse> taskList) {
        return new QuestInfoResponse(
                test.getTitle(),
                test.getContent(),
                test.getCategory().getName(),
                test.getRegisterTime(),
                test.getTestRelateTime().getRecruitmentTimeStart(),
                test.getTestRelateTime().getRecruitmentTimeLimit(),
                test.getTestRelateTime().getDurationTimeStart(),
                test.getTestRelateTime().getDurationTimeLimit(),
                test.getTestRelateTime().getModifyTimeStart(),
                test.getTestRelateTime().getModifyTimeLimit(),
                test.getMaker().getNickname(),
                test.getParticipantCapacity(),
                test.getReward(),
                test.getRequireCondition(),
                test.getPreferenceCondition(),
                taskList
        );
    }
}
