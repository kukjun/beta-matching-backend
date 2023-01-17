package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeApplyToApproveRequest {

    private List<UUID> approveTesterIdList;

    public static ChangeApplyToApproveRequest newInstance(List<UUID> idList) {
        ChangeApplyToApproveRequest request = new ChangeApplyToApproveRequest();
        request.approveTesterIdList = idList;
        return request;
    }

}
