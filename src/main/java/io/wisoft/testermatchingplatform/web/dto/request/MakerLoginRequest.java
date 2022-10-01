package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakerLoginRequest {
    private String email;
    private String password;
}
