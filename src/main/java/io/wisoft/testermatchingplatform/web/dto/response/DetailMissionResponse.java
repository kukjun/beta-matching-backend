package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Mission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailMissionResponse {
    private final UUID id;
    private final String title;
    private final String makerNickname;
    private final String company;
    private final LocalDate recruitmentTimeStart;
    private final LocalDate recruitmentTimeLimit;
    private final LocalDate durationTimeStart;
    private final LocalDate durationTimeLimit;
    private final String content;
    private final long reward;
    private final long apply;
    private final int participantCapacity;
    private final long deadLine;
    private final String symbolImageRoot;

    public static DetailMissionResponse fromMission(Mission mission) {
        DetailMissionResponse response = new DetailMissionResponse(
                mission.getId(),
                mission.getTitle(),
                mission.getMaker().getNickname(),
                mission.getMaker().getCompany(),
                mission.getMissionDate().getRecruitmentTimeStart(),
                mission.getMissionDate().getRecruitmentTimeEnd(),
                mission.getMissionDate().getDurationTimeStart(),
                mission.getMissionDate().getDurationTimeEnd(),
                mission.getContent(),
                mission.getReward(),
                mission.getApplyInformationList().size(),
                mission.getLimitPerformer(),
                mission.remainApplyDays(LocalDate.now()),
                mission.getImageURL()
        );
        return response;
    }
}
