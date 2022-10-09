package io.wisoft.testermatchingplatform.web.dto.response.maker;


import lombok.Getter;

import java.util.List;

@Getter
public class ApplyDTO {
    private String nickname;
    private String status;
    private List<SimpleReviewDTO> beforeTests;

    public static ApplyDTO from(String nickname, String status, List<SimpleReviewDTO> beforeTests) {
        ApplyDTO dto = new ApplyDTO();
        dto.nickname = nickname;
        dto.status = status;
        dto.beforeTests = beforeTests;
        return dto;
    }
}
