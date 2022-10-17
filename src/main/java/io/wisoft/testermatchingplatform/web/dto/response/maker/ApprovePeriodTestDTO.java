package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ApprovePeriodTestDTO {
    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private int reward;
    private int participantCapacity;
    private int apply;
    private String symbolImageRoot;

    private String state;

    public static ApprovePeriodTestDTO fromEntity(Test test, int applyCount, String state) {
        ApprovePeriodTestDTO dto = new ApprovePeriodTestDTO();
        dto.id = test.getId();
        dto.title = test.getTitle();
        dto.makerNickname = test.getMaker().getNickname();
        dto.company = test.getMaker().getCompany();
        dto.reward =test.getReward();
        dto.apply = applyCount;
        dto.participantCapacity = test.getParticipantCapacity();
        dto.symbolImageRoot = test.getSymbolImageRoot();
        dto.state = state;

        return dto;
    }
}
