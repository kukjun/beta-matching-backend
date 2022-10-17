package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.domain.tester.Tester;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CompleteTesterDTO {
    private UUID id;
    private String nickname;
    private long starPoint;
    private String comment;
    private String status;

    public static CompleteTesterDTO fromEntity(UUID id, Tester tester, String status) {
        CompleteTesterDTO dto = new CompleteTesterDTO();
        dto.id = id;
        dto.nickname = tester.getNickname();
        dto.starPoint=0;
        dto.comment = "";
        dto.status = status;
        return dto;
    }
}
