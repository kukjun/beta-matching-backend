package io.wisoft.testermatchingplatform.web.dto.response.tester.dto;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApproveTestDTO {

    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private int reward;
    private String status;
    private String symbolImageRoot;

    public static ApproveTestDTO fromApproveEntity(Test test) {
        return new ApproveTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getReward(),
                "승인",
                test.getSymbolImageRoot()
        );
    }

    public static ApproveTestDTO fromProgressEntity(Test test) {
        return new ApproveTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getReward(),
                "진행중",
                test.getSymbolImageRoot()
        );
    }
}
