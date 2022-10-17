package io.wisoft.testermatchingplatform.web.dto.response.maker;


import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class ApplyDTO {
    private UUID id;
    private String nickname;
    private String status;
    private List<SimpleReviewDTO> beforeTests;

    public static ApplyDTO from(UUID id, String nickname, String status, List<SimpleReviewDTO> beforeTests) {
        ApplyDTO dto = new ApplyDTO();
        dto.id = id;
        dto.nickname = nickname;
        dto.status = status;
        dto.beforeTests = beforeTests;
        return dto;
    }
}
