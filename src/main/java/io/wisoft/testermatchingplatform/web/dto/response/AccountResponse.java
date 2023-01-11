package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Tester;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponse {

    private String account;

    public static AccountResponse fromTester(Tester tester) {
        AccountResponse response = new AccountResponse();
        response.account = tester.getAccountNumber();
        return response;
    }

    public static AccountResponse fromAccount(String account) {
        AccountResponse response = new AccountResponse();
        response.account = account;
        return response;
    }
}