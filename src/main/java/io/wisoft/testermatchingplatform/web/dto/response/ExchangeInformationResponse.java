package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeInformationResponse {
    private final long point;
    private final String account;


    public static ExchangeInformationResponse fromTester(Tester tester) {
        return new ExchangeInformationResponse(
                tester.getPoint(), tester.getAccount()
        );
    }

    public static ExchangeInformationResponse fromMaker(Maker maker) {
        return new ExchangeInformationResponse(
                maker.getPoint(), maker.getAccount()
        );
    }
}
