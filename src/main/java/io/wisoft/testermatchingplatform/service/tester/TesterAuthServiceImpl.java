package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.makerreview.MakerReview;
import io.wisoft.testermatchingplatform.domain.makerreview.MakerReviewRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRepository;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.handler.exception.apply.ApplyNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.apply.ApplyOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterNotFoundException;
import io.wisoft.testermatchingplatform.web.dto.request.PointRequest;
import io.wisoft.testermatchingplatform.web.dto.response.AccountRequest;
import io.wisoft.testermatchingplatform.web.dto.response.AccountResponse;
import io.wisoft.testermatchingplatform.web.dto.response.CashResponse;
import io.wisoft.testermatchingplatform.web.dto.response.nologin.TestListResponse;
import io.wisoft.testermatchingplatform.web.dto.response.tester.*;
import io.wisoft.testermatchingplatform.web.dto.response.tester.dto.ApplyTestDTO;
import io.wisoft.testermatchingplatform.web.dto.response.tester.dto.ApproveTestDTO;
import io.wisoft.testermatchingplatform.web.dto.response.tester.dto.PopularDto;
import io.wisoft.testermatchingplatform.web.dto.response.tester.dto.QuitTestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TesterAuthServiceImpl implements TesterAuthService {

    private final TestRepository testRepository;
    private final TesterRepository testerRepository;

    private final MakerReviewRepository makerReviewRepository;

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
        MakerReview makerReview = new MakerReview(
                starPoint,
                comment,
                new ApplyInformation(applyId)
        );
        makerReviewRepository.save(makerReview);
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
                    System.out.println(test.getTitle() + "l");
                    if (makerReviewRepository.isMakerReview(applyInformation.getId())) {
                        quitTestDTOList.add(QuitTestDTO.fromCompleteReviewEntity(test));
                    } else {
                        quitTestDTOList.add(QuitTestDTO.fromCompleteNotReviewEntity(test));
                    }
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


    @Transactional(readOnly = true)
    public List<TestListResponse> testListByDeadLine(UUID testerId) {
        List<Test> testList = testRepository.findAllByTestByDeadLineExceptApply(testerId);
        return testListResponses(testList);
    }

    @Transactional(readOnly = true)
    public List<TestListResponse> testListByPopular(UUID testerId) {
        List<Test> testList = testRepository.findAllByTestByPopularExceptApply(testerId);

        List<TestListResponse> testListResponses = testListResponses(testList);
        Collections.sort(testListResponses);
        return testListResponses;
    }

    @Transactional
    public List<TestListResponse> testListByCreated(UUID testerId) {
        List<Test> testList = testRepository.findAllByTestByCreatedExceptApply(testerId);
        return testListResponses(testList);
    }

    @Transactional(readOnly = true)
    public List<TestListResponse> testListByTitle(UUID testerId, String title) {
        List<Test> testList = testRepository.findAllByTestByTitleExceptApply(testerId,title);
        return testListResponses(testList);
    }

    @Transactional
    public void applyCancel(UUID testerId, UUID testId) {
        applyInformationRepository.deleteApplyInformationByTesterIdAndTestId(testerId, testId);
    }

    @Transactional
    public AccountResponse addAccount(UUID testerId, AccountRequest accountRequest) {
        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("테스터를 찾을 수 없습니다.")
        );
        tester.setAccountNumber(accountRequest.getAccount());
        AccountResponse response = new AccountResponse();
        response.setAccount(tester.getAccountNumber());
        return response;
    }

    @Transactional
    public AccountResponse updateAccount(UUID testerId, AccountRequest accountRequest) {
        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("테스터를 찾을 수 없습니다.")
        );
        tester.setAccountNumber(accountRequest.getAccount());
        AccountResponse response = new AccountResponse();
        response.setAccount(tester.getAccountNumber());
        return response;
    }

    @Transactional
    public CashResponse changePointToCash(UUID testerId, PointRequest pointRequest) {
        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("테스터를 찾을 수 없습니다.")
        );
        if (tester.getPoint() < pointRequest.getPoint()) {
            // 예외 수정 필요
            throw new RuntimeException("포인트가 부족합니다.");
        }
        else {
            // 현금 전환하는 로직 필요
            tester.setPoint(tester.getPoint() - pointRequest.getPoint());
        }
        CashResponse response = new CashResponse();
        response.setCash(pointRequest.getPoint() * 19 / 20);
        return response;
    }


    private List<TestListResponse> testListResponses(List<Test> testList){
        List<TestListResponse> testListResponses = new ArrayList<>();
        for (Test test : testList) {
            long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
            long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
            int applyCount = applyInformationRepository.countByTestId(test.getId());
            testListResponses.add(TestListResponse.fromEntity(test, applyCount, days));
        }
        return testListResponses;
    }
}
