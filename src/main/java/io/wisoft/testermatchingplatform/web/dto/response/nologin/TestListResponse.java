package io.wisoft.testermatchingplatform.web.dto.response.nologin;

import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TestListResponse implements Comparable<TestListResponse>{

    private final UUID id;

    private final String title;

    private final String makerNickname;

    private final String company;

    private final Long deadlineRemain;

    private final int reward;

    private final int apply;

    private final String symbolImageRoot;

    private final int participantCapacity;


    public static TestListResponse fromEntity(Test test,int applyCount, long days){
        return new TestListResponse(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                days,
                test.getReward(),
                applyCount,
                test.getSymbolImageRoot(),
                test.getParticipantCapacity()
        );
    }

    @Override
    public int compareTo(TestListResponse o) {
        return o.apply - apply;
    }
}
