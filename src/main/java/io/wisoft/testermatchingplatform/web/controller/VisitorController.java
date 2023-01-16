package io.wisoft.testermatchingplatform.web.controller;

import io.wisoft.testermatchingplatform.jwt.JwtProvider;
import io.wisoft.testermatchingplatform.service.MakerService;
import io.wisoft.testermatchingplatform.service.TesterService;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterRequest;
import io.wisoft.testermatchingplatform.web.dto.request.MakerLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.response.CreateMakerResponse;
import io.wisoft.testermatchingplatform.web.dto.response.CreateTesterResponse;
import io.wisoft.testermatchingplatform.web.dto.response.MakerLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.TesterLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class VisitorController {
    private final TesterService testerService;
    private final MakerService makerService;
    // 일단 제외
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @PostMapping("/testers/register")
    public ResponseEntity<CreateTesterResponse> testerRegister(
            @RequestBody CreateTesterRequest request
    ) {
        CreateTesterResponse response = testerService.createTester(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/testers/login")
    public ResponseEntity<TesterLoginResponse> testerLogin(
            @RequestBody TesterLoginRequest request,
            HttpServletResponse header
    ) {
        TesterLoginResponse response = testerService.login(request);
        String accessToken = jwtProvider.createJwtAccessToken(response.getId(), "tester");
        header.setHeader("ACCESS_TOKEN", BEARER_PREFIX + accessToken);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/makers/register")
    public ResponseEntity<CreateMakerResponse> makerRegister(
            @RequestBody CreateMakerRequest request
    ) {
        CreateMakerResponse response = makerService.createMaker(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/makers/login")
    public ResponseEntity<MakerLoginResponse> makerLogin(
            @RequestBody MakerLoginRequest request,
            HttpServletResponse header
    ) {
        MakerLoginResponse response = makerService.login(request);
        String accessToken = jwtProvider.createJwtAccessToken(response.getId(), "maker");
        header.setHeader("ACCESS_TOKEN", BEARER_PREFIX + accessToken);
        return ResponseEntity.ok().body(response);
    }

}
