package io.wisoft.testermatchingplatform.web.dto.response.nologin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TestListResponse {

    private final UUID id;

    private final String title;

    private final String makerNickName;

    private final String company;

    private final Long deadlineRemain;

    private final int reward;

    private final int apply;

    private final String symbolImageRoot;

    private final int participantCapacity;


}
