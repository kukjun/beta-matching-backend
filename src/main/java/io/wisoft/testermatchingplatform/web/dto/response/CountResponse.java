package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CountResponse {
    private final int testerCount;
    private final int makerCount;
    private final int continueTestCount;
    private final int completeTestCount;

    public static CountResponse newInstance(
        final int testerCount,
        final int makerCount,
        final int continueTestCount,
        final int completeTestCount
    ) {
        CountResponse response = new CountResponse(
                testerCount,
                makerCount,
                continueTestCount,
                completeTestCount
        );
        return response;
    }
}
