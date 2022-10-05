package io.wisoft.testermatchingplatform.web.dto.response.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExchangePointResponse {
    private Long point;
    private String accountNumber;
}
