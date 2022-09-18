package io.wisoft.testermatchingplatform.web.dto.resp.quest;

import io.wisoft.testermatchingplatform.domain.quest.Quest;
import io.wisoft.testermatchingplatform.web.dto.resp.task.TaskResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class QuestInfoResponse {

    private Long id;
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

    public static QuestInfoResponse from(final Quest quest, final List<TaskResponse> taskList){
        return new QuestInfoResponse(
                quest.getId(),
                quest.getTitle(),
                quest.getContent(),
                quest.getCategory().getName(),
                quest.getRegisterTime(),
                quest.getQuestRelateTime().getRecruitmentTimeStart(),
                quest.getQuestRelateTime().getRecruitmentTimeLimit(),
                quest.getQuestRelateTime().getDurationTimeStart(),
                quest.getQuestRelateTime().getDurationTimeLimit(),
                quest.getQuestRelateTime().getModifyTimeStart(),
                quest.getQuestRelateTime().getModifyTimeLimit(),
                quest.getQuestMaker().getNickname(),
                quest.getParticipantCapacity(),
                quest.getReward(),
                quest.getRequireCondition(),
                quest.getPreferenceCondition(),
                taskList
        );
    }
}
