package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePerformToCompleteRequest {

    private List<UUID> approveTestIdList;

    public static ChangePerformToCompleteRequest newInstance(List<UUID> idList) {
        ChangePerformToCompleteRequest request = new ChangePerformToCompleteRequest();
        request.approveTestIdList = idList;
        return request;
    }

}
