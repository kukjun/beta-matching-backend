package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRequest {
    private final String account;

    public static AccountRequest newInstance(String account) {
        AccountRequest request = new AccountRequest(account);
        return request;
    }
}
