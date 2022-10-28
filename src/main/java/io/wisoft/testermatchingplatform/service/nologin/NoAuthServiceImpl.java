package io.wisoft.testermatchingplatform.service.nologin;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRepository;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterNotFoundException;
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
public class NoAuthServiceImpl {


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

        return new CountResponse(
                totalTesterCount,
                totalMakerCount,
                totalContinueTestCount,
                totalCompleteTestCount
        );
    }

    @Transactional
    public List<TestListResponse> testList() {
        List<Test> testList = testRepository.findAllByTest();

        List<TestListResponse> testListResponses = new ArrayList<>();
        for (Test test : testList) {
            long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
            long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
            int applyCount = applyInformationRepository.countByTestId(test.getId());
            testListResponses.add(TestListResponse.fromEntity(test, applyCount, days));
        }
        return testListResponses;
    }

    @Transactional
    public List<FastDeadlineResponse> fastDeadline() {
        List<Test> testList = testRepository.findTop4ByTestRelateTime_RecruitmentTimeLimitGreaterThanEqualOrderByTestRelateTime_RecruitmentTimeLimit(new Date());

        List<FastDeadlineResponse> fastDeadlineResponses = new ArrayList<>();
        for (Test test : testList) {
            long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
            long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
            int applyCount = applyInformationRepository.countByTestId(test.getId());
            fastDeadlineResponses.add(FastDeadlineResponse.fromEntity(test,days,applyCount));
        }
        return fastDeadlineResponses;
    }

    @Transactional
    public List<ManyApplyResponse> manyApply() {
        List<Test> top4 = testRepository.getTop4Test();
        // 임시, 임의로 부족한 테스트 추가해주기 -> left outer join 쿼리로 수정해보자.
        if (top4.size() < 4) {
            int garbageTest = 4 - top4.size();
            int count = 0;
            List<Test> testList = testRepository.findAllByTest();
            for (Test test : testList) {
                if (count == garbageTest) break;
                if (applyInformationRepository.countByTestId(test.getId()) != 0) continue;
                top4.add(test);
                count++;
            }
        }

        List<ManyApplyResponse> manyApplies = new ArrayList<>();
        for (Test test : top4) {
            long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
            long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
            int applyCount = applyInformationRepository.countByTestId(test.getId());
            manyApplies.add(ManyApplyResponse.fromEntity(test,days,applyCount));
        }

        return manyApplies;
    }

    @Transactional
    public DetailTestResponse detailTest(final UUID id) {
        Test test = testRepository.findById(id).orElseThrow(
                () -> new TesterNotFoundException("테스트 정보 오류")
        );
        long differenceInMillis = test.getTestRelateTime().getRecruitmentTimeLimit().getTime() - new Date().getTime();
        long days = (differenceInMillis / (24 * 60 * 60 * 1000L)) % 365;
        int applyCount = applyInformationRepository.countByTestId(test.getId());
        return DetailTestResponse.fromEntity(test,days,applyCount);
    }
}
