package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.domain.apply.Apply;
import io.wisoft.testermatchingplatform.domain.apply.ApplyRepository;
import io.wisoft.testermatchingplatform.domain.category.CategoryRepository;
import io.wisoft.testermatchingplatform.domain.quest.Quest;
import io.wisoft.testermatchingplatform.domain.quest.QuestRepository;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.handler.exception.auth.EmailOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.auth.NicknameOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.category.CategoryNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterAuthException;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterNotFoundException;
import io.wisoft.testermatchingplatform.web.dto.req.tester.QuestApplyRequest;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterSignInRequest;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterUpdateRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.tester.QuestApplyListResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.tester.QuestApplyResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.tester.TesterSignInResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.tester.TesterUpdateResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TesterAuthService {

    private final TesterRepository testerRepository;
    private final CategoryRepository categoryRepository;
    private final QuestRepository questRepository;
    private final ApplyRepository applyRepository;

    @Transactional(readOnly = true)
    public TesterSignInResponse loginTester(TesterSignInRequest request) {
        if (!testerRepository.existsByEmailAndPassword(request.getEmail(), request.getPassword())) {
            throw new TesterAuthException("email, password 가 일치하지 않습니다.");
        }

        Tester testerDTO = testerRepository.findByEmail(request.getEmail()).orElseThrow();
        TesterSignInResponse response = new TesterSignInResponse(
                testerDTO.getId(),
                testerDTO.getEmail(),
                testerDTO.getNickname()
        );

        return response;
    }

    @Transactional
    public TesterUpdateResponse updateTester(TesterUpdateRequest testerUpdateRequest, Long testerId) {

        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("tester ID: " + testerId + "를 찾을 수 없습니다."));

        if (testerRepository.existsByEmail(testerUpdateRequest.getEmail())) {
            throw new EmailOverlapException(testerUpdateRequest.getEmail() + "은 중복입니다.");
        }
        if (testerRepository.existsByNickname(testerUpdateRequest.getNickname())) {
            throw new NicknameOverlapException(testerUpdateRequest.getNickname() + "은 중복입니다.");
        }


        tester.setId(testerId);
        tester.setEmail(testerUpdateRequest.getEmail());
        tester.setPassword(testerUpdateRequest.getPassword());
        tester.setPhoneNumber(testerUpdateRequest.getPhoneNumber());
        tester.setPreferCategory(
                categoryRepository.findById(testerUpdateRequest.getPreferCategoryId())
                        .orElseThrow(
                                () -> new CategoryNotFoundException("Category Id: " + testerUpdateRequest.getPreferCategoryId() + "를 찾을 수 없음")
                        )
        );
        tester.setIntroMessage(testerUpdateRequest.getIntroMessage());
        if (!testerUpdateRequest.getIntroPicture().isEmpty()) {

//            이전 사진을 제거하고 새로운 사진을 저장할 수 있도록 하는 로직 필요
            String profilePath = FileHandler.saveProfileFileData(testerUpdateRequest.getIntroPicture());
            tester.setIntroPictureRef(profilePath);

        }

        TesterUpdateResponse response =
                new TesterUpdateResponse(testerRepository.save(tester).getId());

        return response;
    }


    @Transactional
    public QuestApplyResponse applyQuest(QuestApplyRequest questApplyRequest, Long testerId) {
        Tester tester = testerRepository.findById(testerId).orElseThrow();
        Quest quest = questRepository.findById(questApplyRequest.getQuestId()).orElseThrow();

        String requireConditionSubmitPath = FileHandler.saveApplyRequireFileData(questApplyRequest.getRequireConditionSubmit());
        String preferConditionSubmitPath = FileHandler.saveApplyPreferFileData(questApplyRequest.getPreferConditionSubmit());

        Apply apply = new Apply(
                null, tester, quest, requireConditionSubmitPath, preferConditionSubmitPath
        );

        Apply save = applyRepository.save(apply);
        QuestApplyResponse response = new QuestApplyResponse(save.getId());

        return response;

    }

    // 단순 조회
    @Transactional
    public Page<QuestApplyListResponse> findApplyList(Long testerId) {
        Pageable pageable = PageRequest.of(0, 50);
        return applyRepository.findByTesterId(testerId, pageable)
                .map(QuestApplyListResponse::from);

    }

}
