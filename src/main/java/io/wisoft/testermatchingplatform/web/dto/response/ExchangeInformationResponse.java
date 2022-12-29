package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeInformationResponse {
    private final long point;
    private final String account;


    public static ExchangeInformationResponse fromTester(long point, String account) {
        return new ExchangeInformationResponse(
                point, account
        );
    }

    public static ExchangeInformationResponse fromMaker(long point, String account) {
        return new ExchangeInformationResponse(
                point, account
        );
    }
}
