package io.wisoft.testermatchingplatform.web.controller.nologin;


import io.wisoft.testermatchingplatform.service.nologin.NoAuthServiceImpl;
import io.wisoft.testermatchingplatform.web.dto.response.nologin.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NoAuthController {

    private final NoAuthServiceImpl noAuthService;

    @GetMapping("/tests/many_apply")
    public ResponseEntity<List<ManyApplyResponse>> big4(){
        return ResponseEntity.ok().body(noAuthService.manyApply());
    }

    @GetMapping("/tests/fast_deadline")
    public ResponseEntity<List<FastDeadlineResponse>> deadLine4(){
        return ResponseEntity.ok().body(noAuthService.fastDeadline());
    }


    @GetMapping("/tests/")
    public ResponseEntity<DetailTestResponse> detailTest(@RequestParam final UUID test_id){
        return ResponseEntity.ok().body(noAuthService.detailTest(test_id));
    }


    @GetMapping("/counts")
    public ResponseEntity<CountResponse> counts(){
        return ResponseEntity.ok().body(noAuthService.counts());
    }

    @GetMapping("/tests")
    public ResponseEntity<List<TestListResponse>> testList(){
        return ResponseEntity.ok().body(noAuthService.testList());
    }

}
