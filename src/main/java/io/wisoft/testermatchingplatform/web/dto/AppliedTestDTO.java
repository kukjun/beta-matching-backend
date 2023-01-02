package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
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


    public static AppliedTestDTO fromApplyInformation(ApplyInformation applyInformation) {
        return new AppliedTestDTO(
                applyInformation.getTest().getId(),
                applyInformation.getTest().getTitle(),
                applyInformation.getTest().getMaker().getNickname(),
                applyInformation.getTest().getMaker().getCompany(),
                applyInformation.getTest().remainApplyTime(),
                applyInformation.getTest().getReward(),
                applyInformation.getTest().getApplyInformationList().size(),
                applyInformation.getTest().getLimitPerformer(),
                applyInformation.getTest().getImageURL()
        );
    }
}
