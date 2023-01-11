package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePerformToCompleteResponse {
    private final List<UUID> completeTesterIdList = new ArrayList<>();

    public static ChangePerformToCompleteResponse fromApplyInformationList(List<ApplyInformation> applyInformationList) {
        ChangePerformToCompleteResponse response = new ChangePerformToCompleteResponse();
        applyInformationList.forEach((a) -> response.getCompleteTesterIdList().add(a.getId()));
        return response;
    }

}
