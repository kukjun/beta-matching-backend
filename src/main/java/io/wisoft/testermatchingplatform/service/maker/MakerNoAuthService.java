package io.wisoft.testermatchingplatform.service.maker;

import io.wisoft.testermatchingplatform.web.dto.request.MakerLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.MakerRegisterRequest;
import io.wisoft.testermatchingplatform.web.dto.response.maker.MakerLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.maker.MakerRegisterResponse;

public interface MakerNoAuthService {

    MakerRegisterResponse makerRegister(MakerRegisterRequest request);

    MakerLoginResponse makerLogin(MakerLoginRequest request);
}
