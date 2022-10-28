package io.wisoft.testermatchingplatform.web.dto.response.nologin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.wisoft.testermatchingplatform.domain.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DetailTestResponse {

    private final UUID id;

    private final String title;

    private final String makerNickname;

    private final String company;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private final Date recruitmentTimeStart;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private final Date recruitmentTimeLimit;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private final Date durationTimeStart;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private final Date durationTimeLimit;

    private final String content;

    private final int reward;

    private final int apply;

    private final int participantCapacity;

    private final long deadLine;

    private final String symbolImageRoot;

    public static DetailTestResponse fromEntity(Test test, long days, int applyCount){
        return new DetailTestResponse(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getTestRelateTime().getRecruitmentTimeStart(),
                test.getTestRelateTime().getRecruitmentTimeLimit(),
                test.getTestRelateTime().getDurationTimeStart(),
                test.getTestRelateTime().getDurationTimeLimit(),
                test.getContent(),
                test.getReward(),
                applyCount,
                test.getParticipantCapacity(),
                days,
                test.getSymbolImageRoot()
        );
    }

}
