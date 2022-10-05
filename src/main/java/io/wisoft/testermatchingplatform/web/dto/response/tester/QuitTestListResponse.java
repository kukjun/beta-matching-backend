package io.wisoft.testermatchingplatform.web.dto.response.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class QuitTestListResponse {

    private UUID id;
    private String title;
    private String makerNickname;
    private String company;
    private int reward;
    private String status;
    private String symbolImageRoot;
}
