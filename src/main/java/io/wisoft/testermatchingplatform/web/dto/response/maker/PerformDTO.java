package io.wisoft.testermatchingplatform.web.dto.response.maker;

import io.wisoft.testermatchingplatform.domain.tester.Tester;
import lombok.Getter;

@Getter
public class PerformDTO {
    private String nickname;
    private String phoneNumber;
    private String email;
    private String status;

    public static PerformDTO fromEntity(Tester tester, String status) {
        PerformDTO dto = new PerformDTO();
        dto.nickname = tester.getNickname();
        dto.phoneNumber = tester.getPhoneNumber();
        dto.email = tester.getEmail();
        dto.status = status;
        return dto;
    }
}
