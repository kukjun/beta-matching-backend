package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRepository;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReviewRepository;
import io.wisoft.testermatchingplatform.web.dto.response.tester.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TesterAuthService {

    private final TesterRepository testerRepository;

    private final TestRepository testRepository;

    private final TesterReviewRepository testerReviewRepository;

    private final ApplyInformationRepository applyInformationRepository;

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
            ApplyTestListResponse applyTestListResponse = new ApplyTestListResponse(
                    test.getId(),
                    test.getTitle(),
                    test.getMaker().getNickname(),
                    test.getMaker().getCompany(),
                    test.getTestRelateTime().getRecruitmentTimeLimit(),
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
