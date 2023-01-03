package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePerformToCompleteRequest {

    private final List<UUID> approveTestIdList;

    public static ChangePerformToCompleteRequest newInstance(List<UUID> idList) {
        ChangePerformToCompleteRequest request = new ChangePerformToCompleteRequest(idList);
        return request;
    }

}
