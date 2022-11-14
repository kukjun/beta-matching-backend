package io.wisoft.testermatchingplatform.web.controller.tester;

import io.wisoft.testermatchingplatform.service.tester.TesterAuthServiceImpl;
import io.wisoft.testermatchingplatform.web.dto.request.tester.ApplyTestRequest;
import io.wisoft.testermatchingplatform.web.dto.request.tester.CreateReviewRequest;
import io.wisoft.testermatchingplatform.web.dto.response.nologin.TestListResponse;
import io.wisoft.testermatchingplatform.web.dto.response.tester.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/testers")
public class TesterAuthController {

    private final TesterAuthServiceImpl testerAuthService;


    // 테스트 신청 목록 조회하기
    @GetMapping("/{tester_id}/apply")
    public ResponseEntity<TesterTestListResponse> selectApplyTestList(@PathVariable String tester_id){
        TesterTestListResponse testerTestListResponse = testerAuthService.selectApplyTestList(UUID.fromString(tester_id));
        return ResponseEntity.ok().body(testerTestListResponse);
    }

    // 테스트 신청하기
    @PostMapping("/{tester_id}/apply")
    public ResponseEntity<ApplyTestResponse> applyTest(@PathVariable String tester_id, @RequestBody ApplyTestRequest applyTestRequest) throws Exception {
        ApplyTestResponse applyTestResponse = testerAuthService.applyTest(UUID.fromString(tester_id),UUID.fromString(applyTestRequest.getTestId()));
        return ResponseEntity.ok().body(applyTestResponse);
    }

    // 포인트 교환
    @GetMapping("/{tester_id}/exchange")
    public ResponseEntity<ExchangePointResponse> exchangePoint(@PathVariable String tester_id){
        ExchangePointResponse exchangePointResponse = testerAuthService.exchangePoint(UUID.fromString(tester_id));
        return ResponseEntity.ok().body(exchangePointResponse);
    }

    // 리뷰 완료하고 포인트 받기
    @PostMapping("/apply/{apply_id}/review")
    public ResponseEntity<CreateReviewResponse> createReview(@PathVariable String apply_id, @RequestBody CreateReviewRequest createReviewRequest){
        CreateReviewResponse createReviewResponse = testerAuthService.createReview(UUID.fromString(apply_id),createReviewRequest.getStarPoint(),createReviewRequest.getComment());
        return ResponseEntity.ok().body(createReviewResponse);
    }

    // 테스트 수정
    @GetMapping("/{tester_id}/tests/{test_id}")
    public ResponseEntity<ApplyInformationIdResponse> findApplyInformationId(
            @PathVariable("tester_id") UUID testerId,
            @PathVariable("test_id") UUID testId
    ) {
        ApplyInformationIdResponse response = testerAuthService.findApplyTest(testerId, testId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{tester_id}/tests/deadline")
    public ResponseEntity<List<TestListResponse>> testListByDeadLine(
            @PathVariable("tester_id") UUID testerId
    ){
        return ResponseEntity.ok().body(testerAuthService.testListByDeadLine(testerId));
    }

    @GetMapping("/{tester_id}/tests/created")
    public ResponseEntity<List<TestListResponse>> testListByCreated(
            @PathVariable("tester_id") UUID testerId
    ){
        return ResponseEntity.ok().body(testerAuthService.testListByCreated(testerId));
    }

    @GetMapping("/{tester_id}/tests/")
    public ResponseEntity<List<TestListResponse>> testListByCreated(
            @PathVariable("tester_id") UUID testerId,
            @RequestParam final String title
    ){
        return ResponseEntity.ok().body(testerAuthService.testListByTitle(testerId,title));
    }

    @DeleteMapping("/{tester_id}/apply/{apply_id}")
    public ResponseEntity ApplyCancel(
            @PathVariable("tester_id") UUID testerId,
            @PathVariable("apply_id") UUID applyId
    ){
        testerAuthService.ApplyCancel(testerId,applyId);
        return ResponseEntity.ok().body(null);
    }
}
