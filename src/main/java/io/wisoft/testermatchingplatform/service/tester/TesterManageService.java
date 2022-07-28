package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.domain.category.CategoryRepository;
import io.wisoft.testermatchingplatform.domain.grade.Grade;
import io.wisoft.testermatchingplatform.domain.grade.GradeRepository;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.handler.exception.auth.EmailOverlapException;

import io.wisoft.testermatchingplatform.handler.exception.auth.NicknameOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.category.CategoryNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.tester.GradeNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.auth.PhoneNumberOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterNotFoundException;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterSignUpRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.tester.SignUpResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.tester.DetailTesterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TesterManageService {
    private final TesterRepository testerRepository;
    private final CategoryRepository categoryRepository;
    private final GradeRepository gradeRepository;


//    @Transactional
//    public void deleteTester(Long testerId) {
//        if (testerRepository.existsById(testerId)) {
//            testerRepository.deleteById(testerId);
//        } else {
//            throw new TesterNotFoundException("tester ID: " + testerId + "를 찾을 수 없습니다.");
//        }
//    }

    @Transactional(readOnly = true)
    public DetailTesterResponse findByTesterId(Long testerId) {

        Tester tester = testerRepository.findById(testerId)
                .orElseThrow(() -> new TesterNotFoundException("tester ID: " + testerId + "를 찾을 수 없습니다."));

        return new DetailTesterResponse(tester);
    }

    @Transactional
    public SignUpResponse signUp(TesterSignUpRequest request) {

        if (testerRepository.existsByEmail(request.getEmail())) {
            throw new EmailOverlapException(request.getEmail() + ": Email Overlap");
        }
        if (testerRepository.existsByNickname(request.getNickname())) {
            throw new NicknameOverlapException(request.getNickname() + ": Nickname Overlap");
        }
        if (testerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new PhoneNumberOverlapException(request.getPhoneNumber() + ": Phone Number Overlap");
        }

        final String profileImagePath = FileHandler.saveProfileFileData(request.getIntroPicture());

        Tester tester = new Tester(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getPhoneNumber(),
                categoryRepository.findById(request.getPreferCategoryId())
                        .orElseThrow(
                                () -> new CategoryNotFoundException(request.getPreferCategoryId() + "인 카테고리를 찾을 수 없습니다.")),
                request.getIntroMessage(),
                profileImagePath,
                gradeRepository.findById(Grade.initialTesterGrade())
                        .orElseThrow(
                                () -> new GradeNotFoundException("초기 설정용 Grade가 등록되어 있지 않습니다.")
                        )
        );

        tester = testerRepository
                .save(tester);

        return new SignUpResponse(tester.getId());

    }
}

