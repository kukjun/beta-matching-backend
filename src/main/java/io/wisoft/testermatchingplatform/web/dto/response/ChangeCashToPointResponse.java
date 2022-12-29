package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCashToPointResponse {
    private final long point;

    public static ChangeCashToPointResponse newInstance(final long point) {
        return new ChangeCashToPointResponse(point);
    }
}
