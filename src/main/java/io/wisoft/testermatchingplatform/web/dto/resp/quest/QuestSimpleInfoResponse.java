package io.wisoft.testermatchingplatform.web.dto.resp.quest;

import io.wisoft.testermatchingplatform.domain.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;


@Getter
@AllArgsConstructor
public class QuestSimpleInfoResponse {

    private Long id;
    private String title;
    private String content;
    private String categoryName;
    private Timestamp registerTime;
    private Timestamp recruitmentTimeStart;
    private Timestamp recruitmentTimeLimit;
    private String questMakerName;
    private int participantCapacity;
    private int reward;

    public static QuestSimpleInfoResponse from(final Quest quest){
        return new QuestSimpleInfoResponse(
                quest.getId(),
                quest.getTitle(),
                quest.getContent(),
                quest.getCategory().getName(),
                quest.getRegisterTime(),
                quest.getQuestRelateTime().getRecruitmentTimeStart(),
                quest.getQuestRelateTime().getRecruitmentTimeLimit(),
                quest.getQuestMaker().getNickname(),
                quest.getParticipantCapacity(),
                quest.getReward()
        );
    }
}
