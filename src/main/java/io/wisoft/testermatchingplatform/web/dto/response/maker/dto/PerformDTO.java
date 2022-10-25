package io.wisoft.testermatchingplatform.web.dto.response.maker.dto;

import io.wisoft.testermatchingplatform.domain.tester.Tester;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PerformDTO {
    private UUID id;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String status;

    public static PerformDTO fromEntity(UUID id ,Tester tester, String status) {
        PerformDTO dto = new PerformDTO();
        dto.id = id;
        dto.nickname = tester.getNickname();
        dto.phoneNumber = tester.getPhoneNumber();
        dto.email = tester.getEmail();
        dto.status = status;
        return dto;
    }
}
