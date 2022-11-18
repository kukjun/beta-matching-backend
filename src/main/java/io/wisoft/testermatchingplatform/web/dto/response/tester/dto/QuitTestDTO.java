package io.wisoft.testermatchingplatform.web.dto.response.tester.dto;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class QuitTestDTO {

    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private int reward;
    private String status;
    private String symbolImageRoot;

    public static QuitTestDTO fromCompleteReviewEntity(Test test) {
        return new QuitTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getReward(),
                "리뷰 작성완료",
                test.getSymbolImageRoot()
        );
    }

    public static QuitTestDTO fromCompleteNotReviewEntity(Test test) {
        return new QuitTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getReward(),
                "리뷰 작성전",
                test.getSymbolImageRoot()
        );
    }

    public static QuitTestDTO fromNotCompleteEntity(Test test) {
        return new QuitTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getReward(),
                "수행 실패",
                test.getSymbolImageRoot()
        );

    }
}
