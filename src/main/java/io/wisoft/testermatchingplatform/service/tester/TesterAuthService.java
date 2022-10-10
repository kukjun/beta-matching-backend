package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRepository;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReview;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReviewRepository;
import io.wisoft.testermatchingplatform.web.dto.response.tester.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TesterAuthService {

    private final TesterRepository testerRepository;

    private final TestRepository testRepository;

    private final TesterReviewRepository testerReviewRepository;

    private final ApplyInformationRepository applyInformationRepository;

    // 보유 포인트, 계좌 조회하기
    @Transactional
    public ExchangePointResponse exchangePoint(final UUID testerId){
        Tester tester = testerRepository.findById( testerId).orElseThrow(

        );
        return new ExchangePointResponse(
                tester.getPoint(),
                tester.getAccountNumber()
        );
    }

    // 리뷰쓰고 포인트 받기
    @Transactional
    public CreateReviewResponse createReview(final UUID applyId, final int starPoint, final String comment){
        // 리뷰 저장
        TesterReview testerReview = new TesterReview();
        testerReview.setStarPoint(starPoint);
        testerReview.setComment(comment);
        testerReview.setApplyInformation(new ApplyInformation(applyId));
        testerReviewRepository.save(testerReview);
        // 포인트 증가 및 값 반환
        // request 값으로 testerID를 넘겨주면 좋을것같다.
        ApplyInformation applyInfo = applyInformationRepository.findById(applyId).orElseThrow(

        );
        Tester tester = applyInfo.getTester();
        Test test = applyInfo.getTest();
        tester.setPoint(tester.getPoint() + test.getReward());
        return new CreateReviewResponse(testerRepository.save(tester).getPoint());
    }

    // 테스트 신청하기
    @Transactional
    public ApplyTestResponse applyTest(final UUID testerId, final UUID testId){
        ApplyInformation applyInformation = new ApplyInformation();
        applyInformation.setTest(new Test(testId));
        applyInformation.setTester(new Tester(testerId));
        applyInformation.setApproveCheck(false);
        applyInformation.setCompleteCheck(false);
        return new ApplyTestResponse(applyInformationRepository.save(applyInformation).getId());
    }

    // 효율성 변환 및 코드 단순화가 필요!! 신청 테스트 리스트 구하기
    @Transactional
    public TesterTestListResponse selectApplyTestList(final UUID id) {
        return new TesterTestListResponse(
                getApplyTestListResponse(id),
                getApproveTestListResponse(id),
                getQuitTestListResponse(id)
        );
    }

    private List<ApplyTestListResponse> getApplyTestListResponse(final UUID id) {
        List<ApplyTestListResponse> applyTestListResponseList = new ArrayList<>();
        List<UUID> onlyApplyTestId = applyInformationRepository.getOnlyApplyTestId(id);
        for (UUID uuid : onlyApplyTestId) {
            Test test = testRepository.findById(uuid).orElseThrow(

            );
            long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
            long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
            ApplyTestListResponse applyTestListResponse = new ApplyTestListResponse(
                    test.getId(),
                    test.getTitle(),
                    test.getMaker().getNickname(),
                    test.getMaker().getCompany(),
                    days,
                    test.getReward(),
                    test.getParticipantCapacity(),
                    applyInformationRepository.countByTestId(test.getId()),
                    test.getSymbolImageRoot()
            );
            applyTestListResponseList.add(applyTestListResponse);
        }
        return applyTestListResponseList;
    }

    private List<ApproveTestListResponse> getApproveTestListResponse(final UUID id) {
        List<ApproveTestListResponse> approveTestListResponseList = new ArrayList<>();
        List<UUID> approveTestId = applyInformationRepository.getApproveTestId(id);
        for (UUID uuid : approveTestId) {
            Test test = testRepository.findById(uuid).orElseThrow(

            );
            ApproveTestListResponse approveTestListResponse = new ApproveTestListResponse(
                    test.getId(),
                    test.getTitle(),
                    test.getMaker().getNickname(),
                    test.getMaker().getCompany(),
                    test.getReward(),
                    "approve",
                    test.getSymbolImageRoot()
            );
            approveTestListResponseList.add(approveTestListResponse);
        }
        List<UUID> progressTestId = applyInformationRepository.getProgressTestId(id);
        for (UUID uuid : progressTestId) {
            Test test = testRepository.findById(uuid).orElseThrow(

            );
            ApproveTestListResponse approveTestListResponse = new ApproveTestListResponse(
                    test.getId(),
                    test.getTitle(),
                    test.getMaker().getNickname(),
                    test.getMaker().getCompany(),
                    test.getReward(),
                    "progress",
                    test.getSymbolImageRoot()
            );
            approveTestListResponseList.add(approveTestListResponse);
        }

        return approveTestListResponseList;
    }

    private List<QuitTestListResponse> getQuitTestListResponse(final UUID id) {
        List<QuitTestListResponse> quitTestListResponseList = new ArrayList<>();
        List<UUID> notCompleteTestId = applyInformationRepository.getNotCompleteTestId(id);
        for(UUID uuid : notCompleteTestId){
            Test test = testRepository.findById(uuid).orElseThrow(

            );
            QuitTestListResponse quitTestListResponse = new QuitTestListResponse(
                    test.getId(),
                    test.getTitle(),
                    test.getMaker().getNickname(),
                    test.getMaker().getCompany(),
                    test.getReward(),
                    "not complete",
                    test.getSymbolImageRoot()
            );
            quitTestListResponseList.add(quitTestListResponse);
        }

        List<UUID> completeTestId = applyInformationRepository.getCompleteTestId(id);
        for(UUID uuid : completeTestId){
            Test test = testRepository.findById(uuid).orElseThrow(

            );
            QuitTestListResponse quitTestListResponse = new QuitTestListResponse(
                    test.getId(),
                    test.getTitle(),
                    test.getMaker().getNickname(),
                    test.getMaker().getCompany(),
                    test.getReward(),
                    "complete",
                    test.getSymbolImageRoot()
            );
            quitTestListResponseList.add(quitTestListResponse);
        }
        return quitTestListResponseList;
    }
}
