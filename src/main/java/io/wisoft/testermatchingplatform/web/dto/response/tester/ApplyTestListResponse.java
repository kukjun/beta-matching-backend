package io.wisoft.testermatchingplatform.web.dto.response.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ApplyTestListResponse{

    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private Date deadlineRemain;
    private int reward;
    private int apply;
    private int participantCapacity;
    private String symbolImageRoot;
}
