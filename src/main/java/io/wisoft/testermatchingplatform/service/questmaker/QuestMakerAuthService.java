package io.wisoft.testermatchingplatform.service.questmaker;

import io.wisoft.testermatchingplatform.domain.questmaker.QuestMaker;
import io.wisoft.testermatchingplatform.domain.questmaker.QuestMakerRepository;
import io.wisoft.testermatchingplatform.handler.exception.questmaker.QuestMakerNotFoundException;
import io.wisoft.testermatchingplatform.web.dto.req.questmaker.QuestMakerSigninRequest;
import io.wisoft.testermatchingplatform.web.dto.req.questmaker.QuestMakerSignupRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.questmaker.QuestMakerSignInResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.questmaker.QuestMakerSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class QuestMakerAuthService {
    private final QuestMakerRepository questMakerRepository;
    @Transactional
    public QuestMakerSignUpResponse signupQuestMaker(final QuestMakerSignupRequest request){
        QuestMaker questMaker = new QuestMaker(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getPhoneNumber()
        );
        questMaker.checkPassword(request.getConfirmPassword());
        questMaker = questMakerRepository.save(questMaker);
        return QuestMakerSignUpResponse.from(questMaker.getId());
    }

    @Transactional
    public void deleteQuestMaker(final Long questMakerId){
        // 회원 탈퇴
        questMakerRepository.deleteById(questMakerId);
    }

    @Transactional
    public QuestMakerSignInResponse loginQuestMaker(final QuestMakerSigninRequest request){

        QuestMaker questMaker = questMakerRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new QuestMakerNotFoundException("questmaker not found")
        );

        questMaker.checkPassword(request.getPassword());
        return QuestMakerSignInResponse.from(questMaker.getId(), questMaker.getNickname());
    }
}
