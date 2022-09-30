package io.wisoft.testermatchingplatform.web.controller.tester;

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

@RestController
@RequestMapping("/testers")
@RequiredArgsConstructor
public class TesterNoAuthController {

    private final TesterNoAuthService testerNoAuthService;

    @PostMapping("/register")
    public ResponseEntity<TesterRegisterResponse> register(@RequestBody TesterRegisterRequest request) {
        TesterRegisterResponse response = testerNoAuthService.register(request);
        return ResponseEntity.status(202).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TesterLoginResponse> login(@RequestBody TesterLoginRequest request) {
        TesterLoginResponse response = testerNoAuthService.login(request);
        return ResponseEntity.status(200).body(response);
    }
}
