package io.wisoft.testermatchingplatform.web.controller.questmaker;

import io.wisoft.testermatchingplatform.annotation.Login;
import io.wisoft.testermatchingplatform.domain.submit.Submit;
import io.wisoft.testermatchingplatform.handler.exception.questmaker.QuestMakerNotLoginException;
import io.wisoft.testermatchingplatform.service.questmaker.QuestMakerManageService;
import io.wisoft.testermatchingplatform.web.dto.req.apply.ApproveRequest;
import io.wisoft.testermatchingplatform.web.dto.req.quest.QuestRegistRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyIdResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyTesterDetailResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyTesterListResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.quest.QuestIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/questMakers")
public class QuestMakerManageController {

    private final QuestMakerManageService questMakerManageService;

    @PostMapping("/quests")
    public ResponseEntity<QuestIdResponse> registQuest(@Login final Long id,@Validated @RequestBody final QuestRegistRequest request) {
        loginCheck(id);
        QuestIdResponse response = questMakerManageService.registQuest(request, id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/quests")
    public ResponseEntity deleteQuest(@Login final Long id, @RequestParam final Long quest_id) {
        loginCheck(id);
        questMakerManageService.deleteQuest(quest_id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/quests")
    public ResponseEntity<QuestIdResponse> updateQuest(@Login final Long id, @RequestParam final Long quest_id,@Validated @RequestBody final QuestRegistRequest request) {
        loginCheck(id);
        QuestIdResponse response = questMakerManageService.updateQuest(id, quest_id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/quests/{questId}/applies")
    public ResponseEntity<Page<ApplyTesterListResponse>> applyTesterList(
            @Login final Long id, @PathVariable final Long questId
    ) {
        loginCheck(id);
        Page<ApplyTesterListResponse> responseList = questMakerManageService.selectQuestApplyTester(questId);
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/quests/applies")
    public ResponseEntity<ApplyTesterDetailResponse> applyTesterDetail(
            @Login final Long id,
            @RequestParam final Long apply_id
    ) {
        loginCheck(id);
        ApplyTesterDetailResponse response = questMakerManageService.selectApplyTesterDetail(apply_id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/quests/applies")
    public ResponseEntity<ApplyIdResponse> applyTesterApprove(
            @Login final Long id,
            @RequestParam final Long apply_id,
            @Validated @RequestBody final ApproveRequest approveRequest
    ) {
        loginCheck(id);
        ApplyIdResponse response = questMakerManageService.applyTesterApprove(apply_id,approveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    private void loginCheck(Long id) {
        if (id == null) throw new QuestMakerNotLoginException("questmaker not login");
    }

}
