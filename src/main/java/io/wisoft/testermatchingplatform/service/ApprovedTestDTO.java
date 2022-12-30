package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApprovedTestDTO {

    private final UUID id;
    private final String title;
    private final String makerNickname;
    private final String company;
    private final long reward;
    private final String status;
    private final String symbolImageRoot;


    public static ApprovedTestDTO fromTest(Tests test) {
        return new ApprovedTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getReward(),
                test.getStatus().toString(),
                test.getImageURL()
        );
    }
}
