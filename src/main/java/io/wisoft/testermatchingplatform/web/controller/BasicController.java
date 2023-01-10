package io.wisoft.testermatchingplatform.web.controller;

import io.wisoft.testermatchingplatform.service.ApplyInformationService;
import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.web.dto.response.CountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BasicController {

    private final ApplyInformationService applyInformationService;

    @GetMapping("/counts")
    public ResponseEntity<CountResponse> counts() {
        return ResponseEntity.ok().body(applyInformationService.findCount());
    }


}
