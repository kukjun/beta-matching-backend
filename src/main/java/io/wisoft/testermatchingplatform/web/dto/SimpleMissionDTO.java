package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.Mission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleMissionDTO {
    private final UUID id;
    private final String title;
    private final String makerNickname;
    private final String company;
    private final long deadlineRemain;
    private final long reward;
    private final int apply;
    private final String symbolImageRoot;
    private final int participantCapacity;

    public static SimpleMissionDTO fromMission(Mission mission) {
        SimpleMissionDTO dto = new SimpleMissionDTO(
                mission.getId(),
                mission.getTitle(),
                mission.getMaker().getNickname(),
                mission.getMaker().getCompany(),
                mission.remainApplyDays(),
                mission.getReward(),
                mission.getApplyInformationList().size(),
                mission.getImageURL(),
                mission.getLimitPerformer()
        );
        return dto;
    }
}
