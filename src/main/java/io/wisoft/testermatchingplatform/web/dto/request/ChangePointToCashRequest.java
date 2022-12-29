package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePointToCashRequest {
    private final long point;

    public static ChangePointToCashRequest newInstance(
            final long point
    ) {
        ChangePointToCashRequest request = new ChangePointToCashRequest(
                point
        );
        return request;
    }
}
