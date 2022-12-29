package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCashToPointRequest {
    private final long cash;

    public static ChangeCashToPointRequest newInstance(final long cash) {
        return new ChangeCashToPointRequest(cash);
    }

}
