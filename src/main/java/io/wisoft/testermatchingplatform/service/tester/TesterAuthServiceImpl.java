package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReview;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReviewRepository;
import io.wisoft.testermatchingplatform.handler.exception.apply.ApplyNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.apply.ApplyOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterNotFoundException;
import io.wisoft.testermatchingplatform.web.dto.response.tester.*;
import io.wisoft.testermatchingplatform.web.dto.response.tester.dto.ApplyTestDTO;
import io.wisoft.testermatchingplatform.web.dto.response.tester.dto.ApproveTestDTO;
import io.wisoft.testermatchingplatform.web.dto.response.tester.dto.QuitTestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TesterAuthServiceImpl implements TesterAuthService {

    private final TesterRepository testerRepository;

    private final TesterReviewRepository testerReviewRepository;

    private final ApplyInformationRepository applyInformationRepository;

    @Transactional(readOnly = true)
    public ExchangePointResponse exchangePoint(final UUID testerId) {
        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("테스터 정보 오류")
        );
        return new ExchangePointResponse(
                tester.getPoint(),
                tester.getAccountNumber()
        );
    }

    @Transactional
    public CreateReviewResponse createReview(final UUID applyId, final int starPoint, final String comment) {
        // 리뷰 생성 및 db에 저장
        TesterReview testerReview = new TesterReview(
                starPoint,
                comment,
                new ApplyInformation(applyId)
        );
        testerReviewRepository.save(testerReview);
        // 포인트 증가 및 값 반환
        // request 값으로 testerID를 넘겨주면 좋을것같다.
        ApplyInformation applyInfo = applyInformationRepository.findById(applyId).orElseThrow(
                () -> new ApplyNotFoundException("신청 정보 오류")
        );
        Tester tester = applyInfo.getTester();
        Test test = applyInfo.getTest();
        tester.setPoint(tester.getPoint() + test.getReward());
        return new CreateReviewResponse(testerRepository.save(tester).getPoint());
    }

    @Transactional
    public ApplyTestResponse applyTest(final UUID testerId, final UUID testId) {
        UUID uuid = applyInformationRepository.getApplyInfoUUIDByIdAndTesterId(testerId, testId);
        if (uuid != null) {
            // 이미 신청된 정보 -> 예외 처리 "이미 신청되었습니다"
            throw new ApplyOverlapException("이미 신청한 테스트 입니다.");
        }
        // 신청 정보 생성
        ApplyInformation applyInformation = new ApplyInformation(
                new Test(testId),
                new Tester(testerId),
                false,
                false
        );
        return new ApplyTestResponse(applyInformationRepository.save(applyInformation).getId());
    }

    @Transactional(readOnly = true)
    public TesterTestListResponse selectApplyTestList(final UUID id) {
        List<ApplyInformation> list = applyInformationRepository.getApplyTestList(id);
        List<ApplyTestDTO> applyTestDTOList = new ArrayList<>();
        List<ApproveTestDTO> approveTestDTOList = new ArrayList<>();
        List<QuitTestDTO> quitTestDTOList = new ArrayList<>();

        for (ApplyInformation applyInformation : list) {
            Test test = applyInformation.getTest();

            switch (applyInformation.getStatus()) {
                case APPLY:
                    long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
                    long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
                    int applyCount = applyInformationRepository.countByTestId(test.getId());
                    applyTestDTOList.add(ApplyTestDTO.fromEntity(test, applyCount, days));
                    break;
                case APPROVE:
                    approveTestDTOList.add(ApproveTestDTO.fromApproveEntity(test));
                    break;
                case PROGRESS:
                    approveTestDTOList.add(ApproveTestDTO.fromProgressEntity(test));
                    break;
                case NOT_COMPLETE:
                    quitTestDTOList.add(QuitTestDTO.fromNotCompleteEntity(test));
                    break;
                case COMPLETE:
                    quitTestDTOList.add(QuitTestDTO.fromCompleteEntity(test));
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        }

        return new TesterTestListResponse(
                applyTestDTOList,
                approveTestDTOList,
                quitTestDTOList
        );
    }

    public ApplyInformationIdResponse findApplyTest(UUID testerId, UUID testId) {
        UUID applyInformationId = applyInformationRepository.getApplyInformationId(testerId, testId);
        return new ApplyInformationIdResponse(applyInformationId);
    }
}
