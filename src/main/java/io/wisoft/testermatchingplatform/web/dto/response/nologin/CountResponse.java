package io.wisoft.testermatchingplatform.web.dto.response.nologin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountResponse {

    private final Long testerCount;

    private final Long makerCount;

    private final Long continueTestCount;

    private final Long completeTestCount;

}
