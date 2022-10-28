package io.wisoft.testermatchingplatform.web.controller.maker;

import io.wisoft.testermatchingplatform.jwt.JwtProvider;
import io.wisoft.testermatchingplatform.service.maker.MakerNoAuthServiceImpl;
import io.wisoft.testermatchingplatform.web.dto.request.MakerLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.MakerRegisterRequest;
import io.wisoft.testermatchingplatform.web.dto.response.maker.MakerLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.maker.MakerRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/makers")
@RequiredArgsConstructor
public class MakerNoAuthController {

    private final MakerNoAuthServiceImpl makerNoAuthService;

    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<MakerRegisterResponse> register(@RequestBody MakerRegisterRequest request) {
        MakerRegisterResponse response = makerNoAuthService.makerRegister(request);
        return ResponseEntity.status(202).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<MakerLoginResponse> login(@RequestBody MakerLoginRequest request, HttpServletResponse header) {
        MakerLoginResponse response = makerNoAuthService.makerLogin(request);
        String accessToken = jwtProvider.createJwtAccessToken(response.getId(),"maker");
        header.setHeader("ACCESS_TOKEN", BEARER_PREFIX + accessToken);
        return ResponseEntity.status(200).body(response);
    }
}