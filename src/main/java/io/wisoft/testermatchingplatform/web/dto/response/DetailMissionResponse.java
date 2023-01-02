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

    public static DetailMissionResponse fromMission(Mission test) {
        DetailMissionResponse response = new DetailMissionResponse(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getMissionDate().getRecruitmentTimeStart(),
                test.getMissionDate().getRecruitmentTimeEnd(),
                test.getMissionDate().getDurationTimeStart(),
                test.getMissionDate().getDurationTimeEnd(),
                test.getContent(),
                test.getReward(),
                test.getApplyInformationList().size(),
                test.getLimitPerformer(),
                test.remainApplyTime(),
                test.getImageURL()
        );
        return response;
    }
}
