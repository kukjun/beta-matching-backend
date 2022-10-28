package io.wisoft.testermatchingplatform.web.dto.response.nologin;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FastDeadlineResponse {

    private final UUID id;

    private final String title;

    private final String makerNickname;

    private final String company;

    private final Long deadlineRemain;

    private final int reward;

    private final int apply;

    private final int participantCapacity;

    private final String symbolImageRoot;


    public static FastDeadlineResponse fromEntity(Test test,long days,int applyCount){
        return new FastDeadlineResponse(
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
