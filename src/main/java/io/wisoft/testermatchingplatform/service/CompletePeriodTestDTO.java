package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletePeriodTestDTO {
    private final UUID id;
    private final String title;
    private final String makerNickname;
    private final String company;
    private final long reward;
    private final String symbolImageRoot;
    private final String state;

    public static CompletePeriodTestDTO fromTest(Tests test) {
        CompletePeriodTestDTO dto = new CompletePeriodTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getReward(),
                test.getImageURL(),
                test.getStatus().toString()
        );

        return dto;
    }
}
