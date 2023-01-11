package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePointToCashResponse {
    private long cash;

    public static ChangePointToCashResponse newInstance(long cash) {
        ChangePointToCashResponse response = new ChangePointToCashResponse();
        response.cash = cash;
        return response;
    }
}
