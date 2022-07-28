package io.wisoft.testermatchingplatform.web.controller.tester;

import io.wisoft.testermatchingplatform.annotation.Login;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterAuthException;
import io.wisoft.testermatchingplatform.handler.validator.image.ValidationSequence;
import io.wisoft.testermatchingplatform.service.tester.TesterManageService;
import io.wisoft.testermatchingplatform.service.tester.TesterAuthService;
import io.wisoft.testermatchingplatform.web.dto.req.tester.QuestApplyRequest;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterSignInRequest;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterSignUpRequest;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterUpdateRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.tester.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class TesterController {

    final TesterAuthService testerAuthService;
    final TesterManageService testerManageService;

    @Autowired
    TesterController(
            TesterAuthService testerAuthService,
            TesterManageService testerManageService
    ) {
        this.testerAuthService = testerAuthService;
        this.testerManageService = testerManageService;
    }

    @PostMapping("/testers")
    public ResponseEntity<SignUpResponse> registerTester(
            @ModelAttribute
            @Validated(ValidationSequence.class)
            final TesterSignUpRequest testerSignUpRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testerManageService.signUp(testerSignUpRequest));

    }

//    연관 내용 전부 지워야 함.
//    @DeleteMapping("testers/{tester_id}")
//    public ResponseEntity deleteTester(@PathVariable("tester_id") Long testerId) {
//        testerManageService.deleteTester(testerId);
//        return ResponseEntity
//                .noContent().build();
//    }

    @PostMapping("/testers/signin")
    public ResponseEntity<TesterSignInResponse> loginTester(
            @RequestBody @Valid final TesterSignInRequest testerSignInRequest,
            HttpServletRequest httpServletRequest
    ) {
        TesterSignInResponse response = testerAuthService.loginTester(testerSignInRequest);
        // 세션 등록
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("currentTester", response.getId());
        // 5분
        session.setMaxInactiveInterval(1800);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @PostMapping("/testers/{tester_id}/apply")
    public ResponseEntity<QuestApplyResponse> applyQuest(
            HttpServletRequest sessionRequest,
            @PathVariable("tester_id") Long testerId,
            @ModelAttribute QuestApplyRequest questApplyRequest
    ) {
        Long currentTesterId = (Long) sessionRequest.getSession().getAttribute("currentTester");
        TesterLoginCheck(testerId, currentTesterId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testerAuthService.applyQuest(questApplyRequest, testerId));
    }

    @GetMapping("/testers/{tester_id}/apply")
    public ResponseEntity<Page<QuestApplyListResponse>> showApplyQuests(
            HttpServletRequest sessionRequest,
            @PathVariable("tester_id") Long testerId
    ) {
        Long currentTesterId = (Long) sessionRequest.getSession().getAttribute("currentTester");
        TesterLoginCheck(testerId, currentTesterId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testerAuthService.findApplyList(testerId));
    }


    @GetMapping("/testers/{tester_id}")
    public DetailTesterResponse getTesterInformation(@PathVariable("tester_id") Long testerId) {
        return testerManageService.findByTesterId(testerId);
    }

    @PatchMapping("/testers/{tester_id}")
    public ResponseEntity<TesterUpdateResponse> updateTester(
            HttpServletRequest sessionRequest,
            @PathVariable("tester_id") Long testerId,
            @ModelAttribute TesterUpdateRequest testerUpdateRequest
    ) {
        Long currentTesterId = (Long) sessionRequest.getSession().getAttribute("currentTester");
        TesterLoginCheck(currentTesterId, testerId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testerAuthService.updateTester(testerUpdateRequest, testerId));
    }


    private void TesterLoginCheck(Long currentTesterId, Long testerId) {
        if (currentTesterId == null) {
            throw new TesterAuthException("로그인이 되어있지 않은 상태임");
        }
        if (!currentTesterId.equals(testerId)) {
            throw new TesterAuthException("로그인된 사용자와는 다른 사용자 업데이트를 하려고 함.");
        }
    }



}
