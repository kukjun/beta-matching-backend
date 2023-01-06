package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterOfClosedMissionDTO {
    private final UUID id;
    private final String nickname;
    private final int starPoint;
    private final String comment;
    private final String status;

    public static TesterOfClosedMissionDTO fromApplyInformation(ApplyInformation applyInformation) {
        TesterOfClosedMissionDTO response = new TesterOfClosedMissionDTO(
                applyInformation.getId(),
                applyInformation.getTester().getNickname(),
                0,
                "",
                applyInformation.getStatus().toString()
        );

        return response;
    }

}
