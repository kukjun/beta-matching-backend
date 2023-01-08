package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CountResponse {
    private final long testerCount;
    private final long makerCount;
    private final long continueTestCount;
    private final long completeTestCount;

    public static CountResponse newInstance(
        final long testerCount,
        final long makerCount,
        final long continueTestCount,
        final long completeTestCount
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
