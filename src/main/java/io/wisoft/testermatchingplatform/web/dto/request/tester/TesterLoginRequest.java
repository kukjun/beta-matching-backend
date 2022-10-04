package io.wisoft.testermatchingplatform.web.dto.request.tester;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TesterLoginRequest {
    private String email;
    private String password;
}
