package io.wisoft.testermatchingplatform.service.questmaker;//package io.wisoft.testermatchingplatform.service.questmaker;
//
//import io.wisoft.testermatchingplatform.domain.maker.Maker;
//import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
//import io.wisoft.testermatchingplatform.handler.exception.questmaker.QuestMakerNotFoundException;
//import io.wisoft.testermatchingplatform.web.dto.req.questmaker.QuestMakerSigninRequest;
//import io.wisoft.testermatchingplatform.web.dto.req.questmaker.QuestMakerSignupRequest;
//import io.wisoft.testermatchingplatform.web.dto.resp.questmaker.QuestMakerSignInResponse;
//import io.wisoft.testermatchingplatform.web.dto.resp.questmaker.QuestMakerSignUpResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class QuestMakerAuthService {
//    private final MakerRepository makerRepository;
//    @Transactional
//    public QuestMakerSignUpResponse signupQuestMaker(final QuestMakerSignupRequest request){
//        Maker maker = new Maker(
//                request.getEmail(),
//                request.getPassword(),
//                request.getNickname(),
//                request.getPhoneNumber()
//        );
//        maker.checkPassword(request.getConfirmPassword());
//        maker = makerRepository.save(maker);
//        return QuestMakerSignUpResponse.from(maker.getId());
//    }
//
//    @Transactional
//    public void deleteQuestMaker(final Long questMakerId){
//        // 회원 탈퇴
//        makerRepository.deleteById(questMakerId);
//    }
//
//    @Transactional
//    public QuestMakerSignInResponse loginQuestMaker(final QuestMakerSigninRequest request){
//
//        Maker maker = makerRepository.findByEmail(request.getEmail()).orElseThrow(
//                () -> new QuestMakerNotFoundException("maker not found")
//        );
//
//        maker.checkPassword(request.getPassword());
//        return QuestMakerSignInResponse.from(maker.getId());
//    }
//
//    @Transactional
//    public void setRefreshToken(Long id, String token){
//        makerRepository.setRefreshToken(id,token);
//    }
//}
