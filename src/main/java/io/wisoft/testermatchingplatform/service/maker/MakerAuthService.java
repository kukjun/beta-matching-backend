package io.wisoft.testermatchingplatform.service.maker;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.maker.Maker;
import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
import io.wisoft.testermatchingplatform.domain.makerreview.MakerReviewRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRepository;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.domain.testerreview.TesterReviewRepository;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.web.dto.response.maker.CompleteResponse;
import io.wisoft.testermatchingplatform.web.dto.response.maker.CreateTesterReviewResponse;
import io.wisoft.testermatchingplatform.web.dto.request.maker.*;
import io.wisoft.testermatchingplatform.web.dto.response.maker.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MakerAuthService {

    private final MakerRepository makerRepository;
    private final TestRepository testRepository;
    private final ApplyInformationRepository applyInformationRepository;
    private final MakerReviewRepository makerReviewRepository;
    private final TesterReviewRepository testerReviewRepository;
    private final TesterRepository testerRepository;


    // Test 생성
    @Transactional
    public CreateTestResponse createTest(UUID makerId, CreateTestRequest request) {
        Maker maker = makerRepository.findById(makerId).orElseThrow();
        String symbolImageRoot = FileHandler.saveProfileFileData(request.getSymbolImage());
        Test test = request.toEntity(maker, symbolImageRoot);

        // check Logic 들어가야함.
        long needPoint = (long) test.getReward() * test.getParticipantCapacity();
        if (maker.checkAvailableCreateTest(needPoint)) {
            maker.setPoint(maker.getPoint() - needPoint);
            makerRepository.save(maker);
            return new CreateTestResponse(testRepository.save(test).getId());
        } else {
            return new CreateTestResponse(null);
        }

    }

    // Test 수정
    @Transactional
    public PatchTestResponse patchTest(UUID makerId, UUID testId, PatchTestRequest request) {
        Maker maker = makerRepository.findById(makerId).orElseThrow();
        Test test = testRepository.findById(testId).orElseThrow();
        long beforePoint = ((long) test.getReward() * test.getParticipantCapacity());
        String symbolImageRoot = FileHandler.saveProfileFileData(request.getSymbolImage());
        test = request.toEntity(test, symbolImageRoot);

        long needPoint = (long) test.getReward() * test.getParticipantCapacity();
        if(maker.checkAvailableCreateTest(needPoint-beforePoint)) {
            maker.setPoint(maker.getPoint() - (needPoint-beforePoint));
            return new PatchTestResponse(testRepository.save(test).getId());
        } else {
            return new PatchTestResponse(null);
        }
    }

    // Maker가 만든 Test List 조회
    @Transactional
    public TestsFromMakerResponse findTests(UUID makerId) {
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
                        approvePeriodTestDTOList.add(ApprovePeriodTestDTO.fromEntity(test, staticApplyCount, "before Approve"));
                    } else {
                        approvePeriodTestDTOList.add(ApprovePeriodTestDTO.fromEntity(test, staticApplyCount, "Approve"));
                    }
                    break;
                case PROGRESS:
                    if (applyInformationRepository.isApprove(test.getId())) {
                        progressPeriodTestDTOList.add(ProgressPeriodTestDTO.fromEntity(test));
                    }
                    break;
                case COMPLETE:
                    if (makerReviewRepository.isWriteReview(test.getId())) {
                        completePeriodTestDTOList.add(CompletePeriodTestDTO.fromEntity(test, "before write Review"));
                    } else {
                        completePeriodTestDTOList.add(CompletePeriodTestDTO.fromEntity(test, "write Review"));
                    }
                    break;
                default:
                    System.out.println("Error");
            }
        }

        return new TestsFromMakerResponse(
                applyPeriodTestDTOList,
                approvePeriodTestDTOList,
                progressPeriodTestDTOList,
                completePeriodTestDTOList
        );
    }


    @Transactional
    public ApplyResponse findApply(UUID testId) {
        Test test = testRepository.findById(testId).orElseThrow();
        List<ApplyInformation> applyInformationList = applyInformationRepository.findByTestId(test.getId());
        List<ApplyDTO> applyDTOList = new ArrayList<>();

        for (ApplyInformation applyInformation : applyInformationList) {
            Tester tester = testerRepository.findById(applyInformation.getTester().getId()).orElseThrow();

            List<SimpleReviewDTO> simpleReviewDTOList = testerReviewRepository.findByTesterId(tester.getId());
            if (applyInformation.getApproveTime() == null) {
                // from 말고 명시할만한 내용 ..?
                applyDTOList.add(ApplyDTO.from(tester.getNickname(), "not Verify", simpleReviewDTOList));
            } else {
                applyDTOList.add(ApplyDTO.from(tester.getNickname(), "Verify", simpleReviewDTOList));
            }
        }
        return new ApplyResponse(applyDTOList);
    }

    @Transactional
    public PerformListResponse findPerformList(UUID testId) {
        List<ApplyInformation> applyInformationList = applyInformationRepository.findByTestId(testId);
        List<PerformDTO> performDTOList = new ArrayList<>();
        for (ApplyInformation applyInformation : applyInformationList) {
            Tester tester = testerRepository.findById(applyInformation.getTester().getId()).orElseThrow();
            if (applyInformation.getCompleteTime() == null) {
                performDTOList.add(PerformDTO.fromEntity(tester, "not Complete"));
            } else {
                performDTOList.add(PerformDTO.fromEntity(tester, "Complete"));
            }
        }
        return new PerformListResponse(performDTOList);

    }

    @Transactional
    public PaymentResponse findPayment(UUID makerId) {
        Maker maker = makerRepository.findById(makerId).orElseThrow();
        return new PaymentResponse(maker.getPoint(), maker.getAccountNumber());
    }

    @Transactional
    public CompleteResponse changeApplyState(CompleteRequest completeRequest) {
        List<UUID> requestDTOList = completeRequest.getRequestCompleteDTO();
        List<UUID> responseDTOList = new ArrayList<>();
        for (UUID requestDTO : requestDTOList) {
            ApplyInformation applyInformation = applyInformationRepository.findById(requestDTO).orElseThrow();
            applyInformation.setCompleteTime(new Timestamp(new Date().getTime()));
            applyInformation.setCompleteCheck(true);
            responseDTOList.add(applyInformationRepository.save(applyInformation).getId());
        }
        return new CompleteResponse(responseDTOList);
    }

    @Transactional
    public CreateTesterReviewResponse createReviews(CreateTesterReviewRequest request, UUID makerId) {
        List<TesterReviewDTO> testerReviewDTOList = request.getTesterReviewDTOList();
        List<UUID> testerReviewIdList = new ArrayList<>();
        int remainPoint = 0;
        for (TesterReviewDTO testerReviewDTO : testerReviewDTOList) {
            ApplyInformation applyInformation = applyInformationRepository.findById(testerReviewDTO.getApplyInformationId()).orElseThrow();
            if (applyInformation.getCompleteTime() == null) {
                remainPoint += applyInformation.getTest().getReward();
            }
            testerReviewIdList.add(
                    testerReviewRepository.save(testerReviewDTO.toEntity(applyInformation)).getId()
            );
        }
        Maker maker = makerRepository.findById(makerId).orElseThrow();
        maker.setPoint(maker.getPoint() + remainPoint);
        return new CreateTesterReviewResponse(testerReviewIdList);
    }


    @Transactional
    public ConfirmApplyResponse confirmApply(UUID testId, ConfirmApplyRequest request) {
        List<ApproveInformationDTO> approveInformationDTOList = request.getApproveInformationDTOList();
        List<UUID> successApplyUUIDDTO = new ArrayList<>();

        for (ApproveInformationDTO approveInformationDTO : approveInformationDTOList) {
            ApplyInformation applyInformation = applyInformationRepository.findById(approveInformationDTO.getId()).orElseThrow();
            applyInformation = approveInformationDTO.toEntity(applyInformation);
            if (applyInformation.getApproveCheck()) {
                successApplyUUIDDTO.add(applyInformationRepository.save(applyInformation).getId());
            }
        }
        return new ConfirmApplyResponse(successApplyUUIDDTO);
    }
}
