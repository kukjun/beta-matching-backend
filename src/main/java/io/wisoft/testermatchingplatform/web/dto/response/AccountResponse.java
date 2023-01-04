package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponse {

    private final String account;

    public static AccountResponse fromTester(Tester tester) {
        AccountResponse response = new AccountResponse(tester.getAccount());
        return response;
    }

    public static AccountResponse fromAccount(String account) {
        AccountResponse response = new AccountResponse(account);
        return response;
    }
}