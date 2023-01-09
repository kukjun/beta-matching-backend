package io.wisoft.testermatchingplatform.web.dto;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformTesterDTO {
    private final UUID id;
    private final String nickname;
    private final String phoneNumber;
    private final String email;
    private final String status;

    public static PerformTesterDTO fromApplyInformation(ApplyInformation applyInformation) {
        PerformTesterDTO dto = new PerformTesterDTO(
                applyInformation.getTester().getId(),
                applyInformation.getTester().getNickname(),
                applyInformation.getTester().getPhoneNumber(),
                applyInformation.getTester().getEmail(),
                applyInformation.getStatus().toString()
        );

        return dto;
    }
}
