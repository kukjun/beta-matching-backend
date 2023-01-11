package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCashToPointRequest {
    private long cash;

    public static ChangeCashToPointRequest newInstance(
            final long point
    ) {
        ChangeCashToPointRequest request = new ChangeCashToPointRequest();
        request.cash = point;
        return request;
    }
}
