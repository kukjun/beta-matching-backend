package io.wisoft.testermatchingplatform.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeApplyToApproveRequest {

    private final List<UUID> approveTesterIdList;

    public static ChangeApplyToApproveRequest newInstance(List<UUID> idList) {
        ChangeApplyToApproveRequest request = new ChangeApplyToApproveRequest(idList);
        return request;
    }

}
