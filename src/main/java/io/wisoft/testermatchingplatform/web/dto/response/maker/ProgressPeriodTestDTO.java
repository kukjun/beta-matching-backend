package io.wisoft.testermatchingplatform.web.dto.response.maker;

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

    public static ProgressPeriodTestDTO fromEntity(Test test) {
        ProgressPeriodTestDTO dto = new ProgressPeriodTestDTO();
        dto.id = test.getId();
        dto.title = test.getTitle();
        dto.makerNickname = test.getMaker().getNickname();
        dto.company = test.getMaker().getCompany();
        dto.reward =test.getReward();
        dto.symbolImageRoot = test.getSymbolImageRoot();

        return dto;
    }
}
