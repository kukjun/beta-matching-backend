package io.wisoft.testermatchingplatform.service.nologin;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRepository;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.response.nologin.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoAuthService {

    private final TesterRepository testerRepository;

    private final MakerRepository makerRepository;

    private final TestRepository testRepository;

    private final ApplyInformationRepository applyInformationRepository;

    @Transactional
    public CountResponse counts() {
        Long totalTesterCount = testerRepository.count();
        Long totalMakerCount = makerRepository.count();
        Long totalContinueTestCount = testRepository.countContinueTest();
        Long totalCompleteTestCount = testRepository.countCompleteTest();

        CountResponse countResponse = new CountResponse(
                totalTesterCount,
                totalMakerCount,
                totalContinueTestCount,
                totalCompleteTestCount
        );
        return countResponse;
    }

    @Transactional
    public List<TestListResponse> testList() {
        List<Test> testList = testRepository.findAllByOrderByRegisterTime();

        List<TestListResponse> testListResponses = new ArrayList<>();
        for (Test test : testList) {
            long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
            long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
            testListResponses.add(
                    new TestListResponse(
                            test.getId(),
                            test.getTitle(),
                            test.getMaker().getNickname(),
                            test.getMaker().getCompany(),
                            days,
                            test.getReward(),
                            applyInformationRepository.countByTestId(test.getId()),
                            test.getSymbolImageRoot(),
                            test.getParticipantCapacity()
                    )
            );
        }
        return testListResponses;
    }

    @Transactional
    public List<FastDeadlineResponse> fastDeadline() {
        List<Test> testList = testRepository.findTop4ByTestRelateTime_DurationTimeLimitAfterOrderByTestRelateTime_DurationTimeLimit(new Date());

        List<FastDeadlineResponse> fastDeadlineResponses = new ArrayList<>();
        for (Test test : testList) {
            long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
            long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
            fastDeadlineResponses.add(
                    new FastDeadlineResponse(
                            test.getId(),
                            test.getTitle(),
                            test.getMaker().getNickname(),
                            test.getMaker().getCompany(),
                            days,
                            test.getReward(),
                            applyInformationRepository.countByTestId(test.getId()),
                            test.getParticipantCapacity(),
                            test.getSymbolImageRoot()
                    )
            );
        }
        return fastDeadlineResponses;
    }

    @Transactional
    public List<ManyApplyResponse> manyApply() {
        List<UUID> top4 = applyInformationRepository.getTop4Test();

        List<ManyApplyResponse> manyApplies = new ArrayList<>();
        for (UUID uuid : top4) {
            Test test = testRepository.findById(uuid).orElseThrow(

            );
            long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
            long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
            manyApplies.add(

                    new ManyApplyResponse(
                            test.getId(),
                            test.getTitle(),
                            test.getMaker().getNickname(),
                            test.getMaker().getCompany(),
                            days,
                            test.getReward(),
                            applyInformationRepository.countByTestId(uuid),
                            test.getParticipantCapacity(),
                            test.getSymbolImageRoot()
                    )
            );
        }

        return manyApplies;
    }

    @Transactional
    public DetailTestResponse detailTest(final UUID id) {
        Test test = testRepository.findById(id).orElseThrow(
                // 예외 처리
        );
        long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
        long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
        return new DetailTestResponse(
                test.getId(),
                test.getTitle(),
                test.getMaker().getNickname(),
                test.getMaker().getCompany(),
                test.getTestRelateTime().getRecruitmentTimeStart(),
                test.getTestRelateTime().getRecruitmentTimeLimit(),
                test.getTestRelateTime().getDurationTimeStart(),
                test.getTestRelateTime().getDurationTimeLimit(),
                test.getContent(),
                test.getReward(),
                applyInformationRepository.countByTestId(test.getId()),
                test.getParticipantCapacity(),
                days,
                test.getSymbolImageRoot()
        );
    }
}
