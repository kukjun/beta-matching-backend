package io.wisoft.testermatchingplatform.web.controller;

import io.wisoft.testermatchingplatform.service.ApplyInformationService;
import io.wisoft.testermatchingplatform.service.MakerService;
import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.service.TesterReviewService;
import io.wisoft.testermatchingplatform.web.dto.request.*;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/makers")
@RequiredArgsConstructor
public class MakerController {

    private final MakerService makerService;
    private final MissionService missionService;
    private final ApplyInformationService applyInformationService;
    private final TesterReviewService testerReviewService;

    @PostMapping("/{maker_id}/missions")
    public ResponseEntity<CreateMissionResponse> createMission(
            @PathVariable("maker_id") UUID makerId,
            CreateMissionRequest request
    ) {
        CreateMissionResponse response = missionService.createMission(makerId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{maker_id}/missions/{mission_id}")
    public ResponseEntity<UpdateMissionIncludeImageResponse> updateMissionIncludeImage(
            @PathVariable("mission_id") UUID missionId,
            @PathVariable String maker_id,
            UpdateMissionIncludeImageRequest request
    ) {
        UpdateMissionIncludeImageResponse response = missionService.updateIncludeImageMission(missionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{maker_id}/missions/{mission_id}/no_image")
    public ResponseEntity<UpdateMissionExceptImageResponse> updateMissionExceptImage(
            @PathVariable("maker_id") UUID makerId,
            @PathVariable("mission_id") UUID missionId,
            UpdateMissionExceptImageRequest request
    ) {
        UpdateMissionExceptImageResponse response = missionService.updateExceptImageMission(missionId, request);
        URI createdLocation = URI.create("/missions/" + response.getId().toString());
        return ResponseEntity.created(createdLocation).body(response);
    }

    @GetMapping("/{maker_id}/missions")
    public ResponseEntity<ApplyMissionListFromMakerResponse> findMadeMissionList(
            @PathVariable("maker_id") UUID makerId
    ) {
        return ResponseEntity.ok(missionService.madeMissionListFromMaker(makerId));
    }

    @GetMapping("/missions/{mission_id}/apply")
    public ResponseEntity<ApplyTesterListResponse> findApplyTesters(
            @PathVariable("mission_id") UUID missionId
    ) {
        return ResponseEntity.ok().body(applyInformationService.findApplyTesterList(missionId));
    }

    @GetMapping("/missions/{mission_id}/perform")
    public ResponseEntity<PerformTesterListResponse> findPerformTesters(
            @PathVariable("mission_id") UUID missionId
    ) {
        return ResponseEntity.ok().body(applyInformationService.findPerformTesterList(missionId));
    }

    @GetMapping("/missions/{mission_id}/perform/review")
    public ResponseEntity<TesterListOfClosedMissionResponse> findCompleteTesters(
            @PathVariable("mission_id") UUID missionId
    ) {
        return ResponseEntity.ok().body(applyInformationService.findTesterListOfClosedMission(missionId));
    }

    @GetMapping("/{maker_id}/exchange")
    public ResponseEntity<ExchangeInformationResponse> exchangeView(
            @PathVariable("maker_id") UUID makerId
    ) {
        return ResponseEntity.ok().body(makerService.exchangeView(makerId));
    }

    @PatchMapping("/{maker_id}/account")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable("maker_id") UUID makerId,
            @RequestBody final AccountRequest request
    ) {
        return ResponseEntity.ok().body(makerService.updateAccount(makerId, request));
    }

    @PostMapping("/{maker_id}/exchange/point")
    public ResponseEntity<ChangePointToCashResponse> changePointToCash(
            @PathVariable("maker_id") UUID makerId,
            @RequestBody final ChangePointToCashRequest request
    ) {
        return ResponseEntity.ok().body(makerService.changePointToCash(makerId, request));
    }

    @PostMapping("/{maker_id}/exchange/cash")
    public ResponseEntity<ChangeCashToPointResponse> changeCashToPoint(
            @PathVariable("maker_id") UUID makerId,
            @RequestBody final ChangeCashToPointRequest request
    ) {
        return ResponseEntity.ok().body(makerService.changeCashToPoint(makerId, request));
    }

    @PostMapping("/missions/{mission_id}/perform")
    public ResponseEntity<ChangeApplyToApproveResponse> changeApplyToPerform(
            @PathVariable("mission_id") UUID missionId,
            @RequestBody final ChangeApplyToApproveRequest request
    ) {
        return ResponseEntity.ok().body(applyInformationService.applyToApprove(missionId, request));
    }


    @PostMapping("/missions/{mission_id}/complete")
    public ResponseEntity<ChangePerformToCompleteResponse> changePerformToComplete(
            @PathVariable("mission_id") UUID missionId,
            @RequestBody final ChangePerformToCompleteRequest request

    ) {
        return ResponseEntity.ok().body(applyInformationService.performToComplete(missionId, request));
    }

    @PostMapping("/{maker_id}/missions/perform/review")
    public ResponseEntity<CreateTesterReviewListResponse> createReview(
            @PathVariable("maker_id") UUID makerId,
            @RequestBody final CreateTesterReviewListRequest request
    ) {
        return ResponseEntity.ok().body(testerReviewService.createTesterReview(makerId, request));
    }
}
