package io.wisoft.testermatchingplatform.web.controller.maker;

import io.wisoft.testermatchingplatform.service.maker.MakerAuthService;
import io.wisoft.testermatchingplatform.web.dto.request.maker.ConfirmApplyRequest;
import io.wisoft.testermatchingplatform.web.dto.request.maker.CreateTestRequest;
import io.wisoft.testermatchingplatform.web.dto.request.maker.CreateTesterReviewRequest;
import io.wisoft.testermatchingplatform.web.dto.request.maker.PatchTestRequest;
import io.wisoft.testermatchingplatform.web.dto.response.maker.PaymentResponse;
import io.wisoft.testermatchingplatform.web.dto.response.maker.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/makers")
public class MakerAuthController {
    private final MakerAuthService makerAuthService;

    // Test 생성하기
    @PostMapping("/{maker_id}/tests")
    public ResponseEntity<CreateTestResponse> createTest(
            @PathVariable("maker_id") UUID makerId,
            CreateTestRequest request
    ) {
        System.out.println("check controller");
        CreateTestResponse response = makerAuthService.createTest(makerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    // 내가 만든 Test 조회하기
    @GetMapping("/{maker_id}/tests")
    public ResponseEntity<TestsFromMakerResponse> findTests(
            @PathVariable("maker_id") UUID makerId
    ) {
        TestsFromMakerResponse response = makerAuthService.findTests(makerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Test 수정
    @PatchMapping("/{maker_id}/tests/{test_id}")
    public ResponseEntity<PatchTestResponse> patchTest(
            @PathVariable("maker_id") UUID makerId,
            @PathVariable("test_id") UUID testId,
            PatchTestRequest request
    ) {
        PatchTestResponse response = makerAuthService.patchTest(makerId, testId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Test 신청자 List 조회하기
    @GetMapping("/tests/{test_id}/apply")
    public ResponseEntity<ApplyResponse> findApplyInformation(
            @PathVariable("test_id") UUID testId
    ) {
        ApplyResponse response = makerAuthService.findApply(testId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Test 수행인원 List 조회하기
    @GetMapping("/tests/{test_id}/perform")
    public ResponseEntity<PerformListResponse> findPerformInformation(
            @PathVariable("test_id") UUID testId
    ) {
        PerformListResponse response = makerAuthService.findPerformList(testId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 보유 포인트 / 계좌 조회
    @GetMapping("/{maker_id}/exchange")
    public ResponseEntity<PaymentResponse> findPayment(
            @PathVariable("maker_id") UUID makerId
    ) {
        PaymentResponse response = makerAuthService.findPayment(makerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // json List 값 받기 테스트 못함 - Test Data 부재

    //
    @PostMapping("/tests/{test_id}/complete")
    public ResponseEntity<CompleteResponse> changeApplyState(
            @PathVariable("test_id") UUID testId,
            @RequestBody CompleteRequest request
    ) {
        CompleteResponse response = makerAuthService.changeApplyState(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/{maker_id}/tests/perform/review")
    public ResponseEntity<CreateTesterReviewResponse> createReviews(
            @PathVariable("maker_id") UUID makerId,
            @RequestBody CreateTesterReviewRequest request
    ) {
        CreateTesterReviewResponse response = makerAuthService.createReviews(request, makerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/tests/{test_id}/perform")
    public ResponseEntity<ConfirmApplyResponse> confirmApply(
            @PathVariable("test_id") UUID testId,
            @RequestBody ConfirmApplyRequest request) {
        ConfirmApplyResponse response = makerAuthService.confirmApply(testId, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

}
