package io.wisoft.testermatchingplatform.web.dto.resp.quest;

import io.wisoft.testermatchingplatform.domain.test.Test;
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

    public static QuestSimpleInfoResponse from(final Test test){
        return new QuestSimpleInfoResponse(
                test.getId(),
                test.getTitle(),
                test.getContent(),
                test.getCategory().getName(),
                test.getRegisterTime(),
                test.getTestRelateTime().getRecruitmentTimeStart(),
                test.getTestRelateTime().getRecruitmentTimeLimit(),
                test.getMaker().getNickname(),
                test.getParticipantCapacity(),
                test.getReward()
        );
    }
}
