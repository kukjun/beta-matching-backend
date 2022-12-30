package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AppliedTestDTO {
    private final UUID id;
    private final String title;
    private final String makerNickname;
    private final String company;
    private final Long deadlineRemain;
    private final long reward;
    private final int apply;
    private final int participantCapacity;
    private final String symbolImageRoot;


    public static AppliedTestDTO fromTest(Tests test) {
        return new AppliedTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.remainApplyTime(),
                test.getReward(),
                test.getApplyInformationList().size(),
                test.getLimitPerformer(),
                test.getImageURL()
        );
    }
}
