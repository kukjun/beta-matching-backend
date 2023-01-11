package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeInformationResponse {
    private long point;
    private String account;


    public static ExchangeInformationResponse fromTester(Tester tester) {
        ExchangeInformationResponse response = new ExchangeInformationResponse();
        response.account = tester.getAccountNumber();
        response.point = tester.getPoint();
        return response;
    }

    public static ExchangeInformationResponse fromMaker(Maker maker) {
        ExchangeInformationResponse response = new ExchangeInformationResponse();
        response.account = maker.getAccountNumber();
        response.point = maker.getPoint();
        return response;
    }
}
