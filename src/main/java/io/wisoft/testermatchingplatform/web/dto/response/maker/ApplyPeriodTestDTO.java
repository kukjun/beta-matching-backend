package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ApplyPeriodTestDTO {
    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private Long deadlineRemain;
    private int reward;
    private int applyCount;
    private int participantCapacity;
    private String symbolImageRoot;

    public static ApplyPeriodTestDTO fromEntity(Test test, int applyCount) {
        ApplyPeriodTestDTO dto = new ApplyPeriodTestDTO();
        dto.id = test.getId();
        dto.title = test.getTitle();
        dto.makerNickname = test.getMaker().getNickname();
        dto.company = test.getMaker().getCompany();
        dto.deadlineRemain = test.calculateDeadlineRemain();
        dto.reward =test.getReward();
        dto.applyCount = applyCount;
        dto.participantCapacity = test.getParticipantCapacity();
        dto.symbolImageRoot = test.getSymbolImageRoot();

        return dto;
    }

}
