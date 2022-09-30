package io.wisoft.testermatchingplatform.web.dto.response.nologin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DetailTestResponse {

    private final UUID id;

    private final String title;

    private final String makerNickName;

    private final String company;

    private final Date recruitmentTimeStart;

    private final Date recruitmentTimeLimit;

    private final Date durationTimeStart;

    private final Date durationTimeLimit;

    private final String content;

    private final int reward;

    private final int apply;

    private final int participantCapacity;

    private final String symbolImageRoot;

}
