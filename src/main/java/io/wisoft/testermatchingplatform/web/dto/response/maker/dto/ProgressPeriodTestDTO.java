package io.wisoft.testermatchingplatform.web.dto.response.maker.dto;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ProgressPeriodTestDTO {
    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private int reward;
    private String symbolImageRoot;
    private String state;

    public static ProgressPeriodTestDTO fromEntity(Test test, String state) {
        ProgressPeriodTestDTO dto = new ProgressPeriodTestDTO();
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
