package io.wisoft.testermatchingplatform.web.controller;

import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.web.dto.response.DetailMissionResponse;
import io.wisoft.testermatchingplatform.web.dto.response.SimpleMissionListResponse;
import io.wisoft.testermatchingplatform.web.dto.response.Top4MissionListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("")
    public ResponseEntity<SimpleMissionListResponse> missionList() {
        return ResponseEntity.ok().body(missionService.applyMissionList());
    }

    @GetMapping("/")
    public ResponseEntity<DetailMissionResponse> detailMission(@RequestParam("mission_id") final UUID missionId) {
        return ResponseEntity.ok().body(missionService.detailMission(missionId));
    }

    @GetMapping("/many_apply")
    public ResponseEntity<Top4MissionListResponse> popularTop4() {
        return ResponseEntity.ok().body(missionService.top4Popular());
    }

    @GetMapping("/fast_deadline")
    public ResponseEntity<Top4MissionListResponse> deadlineTop4() {
        return ResponseEntity.ok().body(missionService.top4Deadline());
    }

}
