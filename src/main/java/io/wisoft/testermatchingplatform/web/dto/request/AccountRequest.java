package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRequest {
    private String account;

    public static AccountRequest newInstance(String account) {
        AccountRequest request = new AccountRequest();
        request.account = account;
        return request;
    }
}
