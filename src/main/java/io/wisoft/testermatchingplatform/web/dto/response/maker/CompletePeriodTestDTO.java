package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CompletePeriodTestDTO {
    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private int reward;
    private String state;
    private String symbolImageRoot;

    public static CompletePeriodTestDTO fromEntity(Test test, String state) {
        CompletePeriodTestDTO dto = new CompletePeriodTestDTO();
        dto.id = test.getId();
        dto.title = test.getTitle();
        dto.makerNickname = test.getMaker().getNickname();
        dto.company = test.getMaker().getCompany();
        dto.reward =test.getReward();
        dto.symbolImageRoot = test.getSymbolImageRoot();
        dto.state = state;

        return dto;
    }
}
