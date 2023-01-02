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
                applyInformation.getMission().getId(),
                applyInformation.getMission().getTitle(),
                applyInformation.getMission().getMaker().getNickname(),
                applyInformation.getMission().getMaker().getCompany(),
                applyInformation.getMission().remainApplyTime(),
                applyInformation.getMission().getReward(),
                applyInformation.getMission().getApplyInformationList().size(),
                applyInformation.getMission().getLimitPerformer(),
                applyInformation.getMission().getImageURL()
        );
    }
}
