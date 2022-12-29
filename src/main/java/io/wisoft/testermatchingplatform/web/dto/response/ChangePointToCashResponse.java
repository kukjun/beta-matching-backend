package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePointToCashResponse {
    private final long cash;

    public static ChangePointToCashResponse newInstance(long cash) {
        return new ChangePointToCashResponse(cash);
    }
}
