package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
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


    public static ApprovedTestDTO fromApplyInformation(ApplyInformation applyInformation) {
        return new ApprovedTestDTO(
                applyInformation.getMission().getId(),
                applyInformation.getMission().getTitle(),
                applyInformation.getMission().getMaker().getNickname(),
                applyInformation.getMission().getMaker().getCompany(),
                applyInformation.getMission().getReward(),
                applyInformation.getStatus().toString(),
                applyInformation.getMission().getImageURL()
        );
    }
}
