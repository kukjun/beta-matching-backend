package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleTestDTO{
    private final UUID id;
    private final String title;
    private final String makerNickname;
    private final String company;
    private final long deadlineRemain;
    private final long reward;
    private final int apply;
    private final String symbolImageRoot;
    private final int participantCapacity;

    public static SimpleTestDTO fromTest(Tests test) {
        SimpleTestDTO dto = new SimpleTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.remainApplyTime(),
                test.getReward(),
                test.getApplyInformationList().size(),
                test.getImageURL(),
                test.getLimitPerformer()
        );
        return dto;
    }
}
