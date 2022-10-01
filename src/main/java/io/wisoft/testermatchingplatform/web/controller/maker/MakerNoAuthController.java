package io.wisoft.testermatchingplatform.web.controller.maker;

import io.wisoft.testermatchingplatform.service.maker.MakerNoAuthService;
import io.wisoft.testermatchingplatform.web.dto.request.MakerLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.MakerRegisterRequest;
import io.wisoft.testermatchingplatform.web.dto.response.MakerLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.MakerRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/makers")
@RequiredArgsConstructor
public class MakerNoAuthController {

    private final MakerNoAuthService makerNoAuthService;

    @PostMapping("/register")
    public ResponseEntity<MakerRegisterResponse> register(@RequestBody MakerRegisterRequest request) {
        MakerRegisterResponse response = makerNoAuthService.register(request);
        return ResponseEntity.status(202).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<MakerLoginResponse> login(@RequestBody MakerLoginRequest request) {
        MakerLoginResponse response = makerNoAuthService.login(request);
        return ResponseEntity.status(200).body(response);
    }
}