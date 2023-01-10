package io.wisoft.testermatchingplatform.web.controller;

import io.wisoft.testermatchingplatform.service.ApplyInformationService;
import io.wisoft.testermatchingplatform.service.MakerReviewService;
import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.service.TesterService;
import io.wisoft.testermatchingplatform.web.dto.request.AccountRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ApplyMissionRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePointToCashRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerReviewRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/testers")
@RequiredArgsConstructor
public class TesterController {

    private final TesterService testerService;
    private final MissionService missionService;
    private final ApplyInformationService applyInformationService;
    private final MakerReviewService makerReviewService;

    @GetMapping("/{tester_id}/apply")
    public ResponseEntity<ApplyMissionListFromTesterResponse> findApplyMission(
            @PathVariable("tester_id") UUID testerId
    ) {
        return ResponseEntity.ok().body(missionService.applyMissionListFromTester(testerId));
    }

    @PostMapping("/{tester_id}/mission/{mission_id}/apply")
    public ResponseEntity<ApplyMissionResponse> applyMission(
            @PathVariable("tester_id") UUID testerId,
            @PathVariable("mission_id") UUID missionId,
            @RequestBody ApplyMissionRequest request
    ) {
        return ResponseEntity.ok().body(applyInformationService.applyMission(testerId, request.getMissionId()));
    }

    @DeleteMapping("/{tester_id}/mission/{mission_id}/apply")
    // 다르게 처리할 수 있는가?
    public ResponseEntity cancelApply(
            @PathVariable("tester_id") UUID testerId,
            @PathVariable("mission_id") UUID missionId
    ) {
        applyInformationService.cancelApply(testerId, missionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 급하게 만듬
    @GetMapping("/{tester_id}/missions/{mission_id}")
    public ResponseEntity<UUID> findApplyId(
            @PathVariable("tester_id") UUID testerId,
            @PathVariable("mission_id") UUID missionId
    ) {
        return ResponseEntity.ok().body(applyInformationService.findApplyInformationId(testerId, missionId));
    }

    @GetMapping("/{tester_id}/missions/deadline")
    public ResponseEntity<SimpleMissionListResponse> findTestListByDeadLine(
            @PathVariable("tester_id") UUID testerId
    ) {
        return ResponseEntity.ok().body(missionService.applyMissionListByDeadLine(testerId));
    }

    @GetMapping("/{tester_id}/missions/popular")
    public ResponseEntity<SimpleMissionListResponse> findTestListByPopular(
            @PathVariable("tester_id") UUID testerId
    ) {
        return ResponseEntity.ok().body(missionService.applyMissionListByPopular(testerId));
    }

    @GetMapping("/{tester_id}/missions/created")
    public ResponseEntity<SimpleMissionListResponse> findTestListByCreated(
            @PathVariable("tester_id") UUID testerId
    ) {
        return ResponseEntity.ok().body(missionService.applyMissionListByCreated(testerId));
    }

    @GetMapping("/{tester_id}/missions")
    public ResponseEntity<SimpleMissionListResponse> findTestList(
            @PathVariable("tester_id") UUID testerId
    ) {
        return ResponseEntity.ok().body(missionService.applyMissionListByCreated(testerId));
    }

    @GetMapping("/apply/{apply_id}/review")
    public ResponseEntity<CreateMakerReviewResponse> createReview(
            @PathVariable("apply_id") UUID applyInformationId,
            CreateMakerReviewRequest request
    ) {
        return ResponseEntity.ok().body(makerReviewService.createMakerReview(applyInformationId, request));
    }

    @GetMapping("/{tester_id}/exchange")
    public ResponseEntity<ExchangeInformationResponse> exchangeView(
            @PathVariable("tester_id") UUID testerId
    ) {
        return ResponseEntity.ok().body(testerService.exchangeView(testerId));
    }

    @PatchMapping("/{tester_id}/account")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable("tester_id") UUID testerId,
            @RequestBody final AccountRequest request
    ) {
        return ResponseEntity.ok().body(testerService.updateAccount(testerId, request));
    }

    @PostMapping("/{tester_id}/exchange/point")
    public ResponseEntity<ChangePointToCashResponse> changePointToCash(
            @PathVariable("tester_id") UUID testerId,
            @RequestBody final ChangePointToCashRequest request
    ) {
        return ResponseEntity.ok().body(testerService.changePointToCash(testerId, request));
    }




}
