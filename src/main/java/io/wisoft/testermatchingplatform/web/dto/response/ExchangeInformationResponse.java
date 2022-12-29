package io.wisoft.testermatchingplatform.web.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeInformationResponse {
    private final UUID id;
    private final String account;


    public static ExchangeInformationResponse fromTester(UUID id, String account) {
        return new ExchangeInformationResponse(
                id, account
        );
    }

    public static ExchangeInformationResponse fromMaker(UUID id, String account) {
        return new ExchangeInformationResponse(
                id, account
        );
    }
}
