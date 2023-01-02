package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ExecutedTestDTO {

    private final UUID id;
    private final String title;
    private final String makerNickname;
    private final String company;
    private final long reward;
    private final String status;
    private final String symbolImageRoot;


    public static ExecutedTestDTO fromApplyInformation(ApplyInformation applyInformation) {
        return new ExecutedTestDTO(
                applyInformation.getTest().getId(),
                applyInformation.getTest().getTitle(),
                applyInformation.getTest().getMaker().getNickname(),
                applyInformation.getTest().getMaker().getCompany(),
                applyInformation.getTest().getReward(),
                applyInformation.getStatus().toString(),
                applyInformation.getTest().getImageURL()
        );
    }
}
