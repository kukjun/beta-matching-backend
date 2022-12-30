package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailTestResponse {
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

    public static DetailTestResponse fromTest(Tests test) {
        DetailTestResponse response = new DetailTestResponse(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getTestDate().getRecruitmentTimeStart(),
                test.getTestDate().getRecruitmentTimeEnd(),
                test.getTestDate().getDurationTimeStart(),
                test.getTestDate().getDurationTimeEnd(),
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
