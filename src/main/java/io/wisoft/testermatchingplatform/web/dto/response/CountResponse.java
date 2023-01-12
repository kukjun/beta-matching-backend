package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CountResponse {
    private long testerCount;
    private long makerCount;
    private long continueTestCount;
    private long completeTestCount;

    public static CountResponse newInstance(
        final long testerCount,
        final long makerCount,
        final long continueTestCount,
        final long completeTestCount
    ) {
        CountResponse response = new CountResponse();
        response.testerCount = testerCount;
        response.makerCount = makerCount;
        response.continueTestCount = continueTestCount;
        response.completeTestCount = completeTestCount;
        return response;
    }
}
