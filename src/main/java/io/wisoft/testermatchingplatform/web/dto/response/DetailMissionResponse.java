package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Mission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailMissionResponse {
    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private LocalDate recruitmentTimeStart;
    private LocalDate recruitmentTimeLimit;
    private LocalDate durationTimeStart;
    private LocalDate durationTimeLimit;
    private String content;
    private long reward;
    private long apply;
    private int participantCapacity;
    private long deadLine;
    private String symbolImageRoot;

    public static DetailMissionResponse fromMission(Mission mission) {
        DetailMissionResponse response = new DetailMissionResponse();
        response.id = mission.getId();
        response.title = mission.getTitle();
        response.makerNickname = mission.getMaker().getNickname();
        response.company = mission.getMaker().getCompany();
        response.recruitmentTimeStart = mission.getMissionDate().getRecruitmentTimeStart();
        response.recruitmentTimeLimit = mission.getMissionDate().getRecruitmentTimeEnd();
        response.durationTimeStart = mission.getMissionDate().getDurationTimeStart();
        response.durationTimeLimit = mission.getMissionDate().getDurationTimeEnd();
        response.content = mission.getContent();
        response.reward = mission.getReward();
        response.apply = mission.getApplyInformationList().size();
        response.participantCapacity = mission.getLimitPerformer();
        response.deadLine = mission.remainApplyDays(LocalDate.now());
        response.symbolImageRoot = mission.getImageURL();
        return response;
    }
}
