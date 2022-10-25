package io.wisoft.testermatchingplatform.web.controller.maker;

import io.wisoft.testermatchingplatform.service.maker.MakerAuthService;
import io.wisoft.testermatchingplatform.web.dto.request.maker.*;
import io.wisoft.testermatchingplatform.web.dto.response.maker.FindCompleteTesterListResponse;
import io.wisoft.testermatchingplatform.web.dto.response.maker.FindPaymentResponse;
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
    public ResponseEntity<findTestsResponse> findTests(
            @PathVariable("maker_id") UUID makerId
    ) {
        findTestsResponse response = makerAuthService.findTests(makerId);
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
    public ResponseEntity<FindApplyResponse> findApplyInformation(
            @PathVariable("test_id") UUID testId
    ) {
        FindApplyResponse response = makerAuthService.findApply(testId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Test 수행인원 List 조회하기 (수행인원 완료를 위함)
    @GetMapping("/tests/{test_id}/perform")
    public ResponseEntity<FindPerformListResponse> findPerformInformation(
            @PathVariable("test_id") UUID testId
    ) {
        FindPerformListResponse response = makerAuthService.findPerformList(testId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Test 수행인원 List 조회하기 (수행인원 Review 작성을 위함)
    @GetMapping("/tests/{test_id}/perform/review")
    public ResponseEntity<FindCompleteTesterListResponse> findCompleteTester(
            @PathVariable("test_id") UUID testId
    ) {
        FindCompleteTesterListResponse response = makerAuthService.findCompleteTester(testId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 보유 포인트 / 계좌 조회
    @GetMapping("/{maker_id}/exchange")
    public ResponseEntity<FindPaymentResponse> findPayment(
            @PathVariable("maker_id") UUID makerId
    ) {
        FindPaymentResponse response = makerAuthService.findPayment(makerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // json List 값 받기 테스트 못함 - Test Data 부재

    // 선정인원 완료 처리하기
    @PostMapping("/tests/{test_id}/complete")
    public ResponseEntity<ChangeApplyStateResponse> changeApplyState(
            @PathVariable("test_id") UUID testId,
            @RequestBody CompleteRequest request
    ) {
        ChangeApplyStateResponse response = makerAuthService.changeApplyState(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    // 수행인원에 대한 Review 작성하기
    @PostMapping("/{maker_id}/tests/perform/review")
    public ResponseEntity<CreateTestersReviewResponse> createReviews(
            @PathVariable("maker_id") UUID makerId,
            @RequestBody CreateTestersReviewRequest request
    ) {
        CreateTestersReviewResponse response = makerAuthService.createTestersReview(request, makerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    // 신청자를 수행인원으로 변경
    @PostMapping("/tests/{test_id}/perform")
    public ResponseEntity<ConfirmApplyResponse> confirmApply(
            @PathVariable("test_id") UUID testId,
            @RequestBody ConfirmApplyRequest request) {
        ConfirmApplyResponse response = makerAuthService.confirmApply(testId, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

}
