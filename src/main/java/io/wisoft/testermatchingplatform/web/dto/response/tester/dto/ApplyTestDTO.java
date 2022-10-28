package io.wisoft.testermatchingplatform.web.dto.response.tester.dto;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApplyTestDTO {

    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private Long deadlineRemain;
    private int reward;
    private int apply;
    private int participantCapacity;
    private String symbolImageRoot;

    public static ApplyTestDTO fromEntity(Test test,int applyCount,long days){
        return new ApplyTestDTO(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                days,
                test.getReward(),
                applyCount,
                test.getParticipantCapacity(),
                test.getSymbolImageRoot()
        );
    }
}
