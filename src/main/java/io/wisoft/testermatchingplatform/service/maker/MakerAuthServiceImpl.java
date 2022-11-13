package io.wisoft.testermatchingplatform.service.maker;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.maker.Maker;
import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRepository;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReview;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReviewRepository;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.handler.exception.maker.*;
import io.wisoft.testermatchingplatform.web.dto.request.maker.dto.TesterReviewDTO;
import io.wisoft.testermatchingplatform.web.dto.response.maker.ChangeApplyStateResponse;
import io.wisoft.testermatchingplatform.web.dto.response.maker.CreateTestersReviewResponse;
import io.wisoft.testermatchingplatform.web.dto.request.maker.*;
import io.wisoft.testermatchingplatform.web.dto.response.maker.*;
import io.wisoft.testermatchingplatform.web.dto.response.maker.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MakerAuthServiceImpl implements MakerAuthService{

    private final MakerRepository makerRepository;
    private final TestRepository testRepository;
    private final ApplyInformationRepository applyInformationRepository;
    private final TesterReviewRepository testerReviewRepository;
    private final TesterRepository testerRepository;


    // Test 생성
    @Override
    @Transactional
    public CreateTestResponse createTest(UUID makerId, @RequestBody CreateTestRequest request) {
        Maker maker = makerRepository.findById(makerId).orElseThrow();
        String symbolImageRoot = FileHandler.saveProfileFileData(request.getSymbolImage());
        Test test = request.toEntity(maker, symbolImageRoot);

        // check Logic 들어가야함.
        long needPoint = (long) test.getReward() * test.getParticipantCapacity();
        if (maker.checkAvailableCreateTest(needPoint)) {
            maker.setPoint(maker.getPoint() - needPoint);
            return new CreateTestResponse(testRepository.save(test).getId());
        } else {
            throw new MakerCreateTestFailException("생성하기 위한 잔여 Point가 부족합니다.\n 필요한 Point: " + needPoint + ", 잔여 Point: " + maker.getPoint());
        }

    }

    // Test 수정
    @Override
    @Transactional
    public PatchTestResponse patchTest(UUID makerId, UUID testId, @RequestBody PatchTestRequest request) {
        Maker maker = makerRepository.findById(makerId).orElseThrow();
        Test test = testRepository.findById(testId).orElseThrow();
        long beforePoint = ((long) test.getReward() * test.getParticipantCapacity());
        String symbolImageRoot = FileHandler.saveProfileFileData(request.getSymbolImage());
        test = request.toEntity(test, symbolImageRoot);

        long needPoint = (long) test.getReward() * test.getParticipantCapacity();
        if (maker.checkAvailableCreateTest(needPoint - beforePoint)) {
            maker.setPoint(maker.getPoint() - (needPoint - beforePoint));
            return new PatchTestResponse(testRepository.save(test).getId());
        } else {
            throw new MakerRevertTestFailException("수정하기 위한 잔여 Point가 부족합니다.\n 필요한 Point: " + (needPoint-beforePoint ) + ", 잔여 Point: " + maker.getPoint());
        }
    }

    // Maker가 만든 Test List 조회
    @Override
    @Transactional(readOnly = true)
    public findTestsResponse findTests(UUID makerId) {
        List<Test> list = testRepository.findByMaker_Id(makerId);
        List<ApplyPeriodTestDTO> applyPeriodTestDTOList = new ArrayList<>();
        List<ApprovePeriodTestDTO> approvePeriodTestDTOList = new ArrayList<>();
        List<ProgressPeriodTestDTO> progressPeriodTestDTOList = new ArrayList<>();
        List<CompletePeriodTestDTO> completePeriodTestDTOList = new ArrayList<>();

        for (Test test : list) {
            switch (test.getPeriod()) {
                case BEFORE_RECRUITMENT:
                    applyPeriodTestDTOList.add(ApplyPeriodTestDTO.fromEntity(test, 0));
                    break;
                case RECRUITMENT:
                    int applyCount = applyInformationRepository.countByTestId(test.getId());
                    applyPeriodTestDTOList.add(ApplyPeriodTestDTO.fromEntity(test, applyCount));
                    break;
                case AFTER_RECRUITMENT:
                    int staticApplyCount = applyInformationRepository.countByTestId(test.getId());
                    if (applyInformationRepository.isApprove(test.getId())) {
                        approvePeriodTestDTOList.add(ApprovePeriodTestDTO.fromEntity(test, staticApplyCount, "승인"));
                    } else {
                        approvePeriodTestDTOList.add(ApprovePeriodTestDTO.fromEntity(test, staticApplyCount, "승인 전"));
                    }
                    break;
                case PROGRESS:
                    if (applyInformationRepository.isApprove(test.getId())) {
                        if (!applyInformationRepository.isNotAllComplete(test.getId())) {
                            progressPeriodTestDTOList.add(ProgressPeriodTestDTO.fromEntity(test, "전원 수행완료"));
                        } else {
                            progressPeriodTestDTOList.add(ProgressPeriodTestDTO.fromEntity(test, "완료 전"));
                        }
                    }
                    break;
                case COMPLETE:
                    if (testerReviewRepository.isTesterReview(test.getId())) {
                        completePeriodTestDTOList.add(CompletePeriodTestDTO.fromEntity(test, "리뷰 완료"));
                    } else {
                        completePeriodTestDTOList.add(CompletePeriodTestDTO.fromEntity(test, "리뷰 작성전"));
                    }
                    break;
                default:
                    System.out.println("Error");
            }
        }

        return new findTestsResponse(
                applyPeriodTestDTOList,
                approvePeriodTestDTOList,
                progressPeriodTestDTOList,
                completePeriodTestDTOList
        );
    }


    @Override
    @Transactional(readOnly = true)
    public FindApplyResponse findApply(UUID testId) {
        Test test = testRepository.findById(testId).orElseThrow();
        List<ApplyInformation> applyInformationList = applyInformationRepository.findByTestId(test.getId());
        List<ApplyDTO> applyDTOList = new ArrayList<>();

        for (ApplyInformation applyInformation : applyInformationList) {
            Tester tester = testerRepository.findById(applyInformation.getTester().getId()).orElseThrow();

            List<SimpleReviewDTO> simpleReviewDTOList = testerReviewRepository.findByTesterId(tester.getId());
            if (applyInformation.getApproveTime() == null) {
                applyDTOList.add(ApplyDTO.from(applyInformation.getId(), tester.getNickname(), "선정 안함", simpleReviewDTOList));
            } else {
                applyDTOList.add(ApplyDTO.from(applyInformation.getId(), tester.getNickname(), "선정 함", simpleReviewDTOList));
            }
        }
        return new FindApplyResponse(applyDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public FindPerformListResponse findPerformList(UUID testId) {
        List<ApplyInformation> applyInformationList = applyInformationRepository.findByTestId(testId);
        List<PerformDTO> performDTOList = new ArrayList<>();
        for (ApplyInformation applyInformation : applyInformationList) {
            Tester tester = testerRepository.findById(applyInformation.getTester().getId()).orElseThrow();
            if (applyInformation.getCompleteTime() != null) {
                performDTOList.add(PerformDTO.fromEntity(applyInformation.getId(), tester, "테스트 수행 완료"));
            } else {
                performDTOList.add(PerformDTO.fromEntity(applyInformation.getId(), tester, "테스트 수행 이전"));
            }
        }
        return new FindPerformListResponse(performDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public FindCompleteTesterListResponse findCompleteTester(UUID testId) {
        List<ApplyInformation> applyInformationList = applyInformationRepository.findByTestId(testId);
        List<CompleteTesterDTO> completeTesterDTOList = new ArrayList<>();

        for(ApplyInformation applyInformation : applyInformationList) {
            System.out.println(applyInformation.getTester());
            Tester tester = testerRepository.findById(applyInformation.getTester().getId()).orElseThrow();
            if (applyInformation.getCompleteTime() != null) {
                completeTesterDTOList.add(CompleteTesterDTO.fromEntity(applyInformation.getId(), tester, "테스트 수행 완료"));
            } else {
                completeTesterDTOList.add(CompleteTesterDTO.fromEntity(applyInformation.getId(), tester, "테스트 수행 실패"));
            }
        }
        return new FindCompleteTesterListResponse(completeTesterDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public FindPaymentResponse findPayment(UUID makerId) {
        Maker maker = makerRepository.findById(makerId).orElseThrow();
        return new FindPaymentResponse(maker.getPoint(), maker.getAccountNumber());
    }

    @Override
    @Transactional
    public ChangeApplyStateResponse changeApplyState(@RequestBody CompleteRequest completeRequest) {
        List<UUID> requestDTOList = completeRequest.getCompleteTesterIdDTOList();
        List<UUID> responseDTOList = new ArrayList<>();
        for (UUID requestDTO : requestDTOList) {
            ApplyInformation applyInformation = applyInformationRepository.findById(requestDTO).orElseThrow();
            if(applyInformation.getCompleteTime() != null) {
                throw new MakerCompleteOverlapException("수행완료 처리를 이미 한 다음 다시 사용자들을 수행완료 처리하려고 함.");
            }
            applyInformation.setCompleteTime(new Timestamp(new Date().getTime()));
            applyInformation.setCompleteCheck(true);
            responseDTOList.add(applyInformationRepository.save(applyInformation).getId());
        }
        return new ChangeApplyStateResponse(responseDTOList);
    }

    @Override
    @Transactional
    public CreateTestersReviewResponse createTestersReview(@RequestBody CreateTestersReviewRequest request, UUID makerId) {
        List<TesterReviewDTO> testerReviewDTOList = request.getTesterReviewDTOList();
        List<UUID> testerReviewIdList = new ArrayList<>();
        int remainPoint = 0;
        for (TesterReviewDTO testerReviewDTO : testerReviewDTOList) {
            ApplyInformation applyInformation = applyInformationRepository.findById(testerReviewDTO.getId()).orElseThrow();
            if (applyInformation.getCompleteTime() == null) {
                remainPoint += applyInformation.getTest().getReward();
            }
            if (testerReviewRepository.existsByApplyInformation_Id(applyInformation.getId())) {
                throw new MakerReviewOverlapException("이미 한번 리뷰 절차를 진행한 후, 다시 사용자를 리뷰하려고 함");
            }
            TesterReview testerReview = testerReviewDTO.toEntity(applyInformation);

            testerReviewIdList.add(
                    testerReviewRepository.save(testerReview).getId()
            );
        }
        Maker maker = makerRepository.findById(makerId).orElseThrow();
        maker.setPoint(maker.getPoint() + remainPoint);
        return new CreateTestersReviewResponse(testerReviewIdList);
    }


    @Override
    @Transactional
    public ConfirmApplyResponse confirmApply(UUID testId, @RequestBody ConfirmApplyRequest request) {
        List<UUID> approveTesterList = request.getApproveTesterList();
        List<UUID> successApplyUUIDDTO = new ArrayList<>();

        // 수행인원으로 정한 인원 수행 Check 진행
        for (UUID approveTesterId : approveTesterList) {
            ApplyInformation applyInformation = applyInformationRepository.findById(
                    approveTesterId
            ).orElseThrow();
            if(applyInformation.getApproveTime() != null) {
                throw new MakerApproveOverlapException("이미 한번 수행인원 선정 절차를 진행한 후 사용자를 다시 선정하려고 함.");
            }
            applyInformation.setApproveTime(new Timestamp(new Date().getTime()));
            applyInformation.setApproveCheck(true);
            successApplyUUIDDTO.add(applyInformation.getId());
        }

        // 수행인원으로 선정하지 않은 인원도 선정시간 등록
        List<ApplyInformation> applyInformationList = applyInformationRepository.findByTestId(testId);
        for (ApplyInformation applyInformation : applyInformationList) {
            if (applyInformation.getApproveTime() == null) {
                applyInformation.setApproveTime(new Timestamp(new Date().getTime()));
            }
        }

        return new ConfirmApplyResponse(successApplyUUIDDTO);
    }


}
