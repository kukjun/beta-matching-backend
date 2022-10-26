package io.wisoft.testermatchingplatform.web.controller.tester;

import io.wisoft.testermatchingplatform.jwt.JwtProvider;
import io.wisoft.testermatchingplatform.service.tester.TesterNoAuthService;
import io.wisoft.testermatchingplatform.web.dto.request.tester.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.tester.TesterRegisterRequest;
import io.wisoft.testermatchingplatform.web.dto.response.tester.TesterLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.tester.TesterRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/testers")
@RequiredArgsConstructor
public class TesterNoAuthController {

    private final TesterNoAuthService testerNoAuthService;

    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<TesterRegisterResponse> register(@RequestBody TesterRegisterRequest request) {
        TesterRegisterResponse response = testerNoAuthService.register(request);
        return ResponseEntity.status(202).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TesterLoginResponse> login(@RequestBody TesterLoginRequest request, HttpServletResponse header) {
        TesterLoginResponse response = testerNoAuthService.login(request);
        String accessToken = jwtProvider.createJwtAccessToken(response.getId(),"tester");
        header.setHeader("ACCESS_TOKEN",BEARER_PREFIX+ accessToken);
        System.out.println(accessToken);
        return ResponseEntity.status(200).body(response);
    }
}
