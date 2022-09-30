package io.wisoft.testermatchingplatform.service.nologin;

import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRepository;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.response.nologin.CountResponse;
import io.wisoft.testermatchingplatform.web.dto.response.nologin.DetailTestResponse;
import io.wisoft.testermatchingplatform.web.dto.response.nologin.FastDeadlineResponse;
import io.wisoft.testermatchingplatform.web.dto.response.nologin.TestListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public CountResponse counts(){
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
    public List<TestListResponse> testList(){
        List<Test> testList = testRepository.findAllByOrderByRegisterTime();

        List<TestListResponse> testListResponses = new ArrayList<>();
        for(Test test:testList){
            testListResponses.add(
                    new TestListResponse(
                            test.getId(),
                            test.getTitle(),
                            test.getMaker().getNickname(),
                            test.getMaker().getCompany(),
                            test.getTestRelateTime().getRecruitmentTimeLimit(),
                            test.getReward(),
                            applyInformationRepository.countByTestId(test.getId()),
                            test.getSymbolImageRoot()
                    )
            );
        }
        return testListResponses;
    }

    @Transactional
    public List<FastDeadlineResponse> fastDeadline(){
        List<Test> testList = testRepository.findTop4ByOrderByTestRelateTime_DurationTimeLimit();

        List<FastDeadlineResponse> fastDeadlineResponses = new ArrayList<>();
        for(Test test:testList){
            fastDeadlineResponses.add(
                    new FastDeadlineResponse(
                            test.getId(),
                            test.getTitle(),
                            test.getMaker().getNickname(),
                            test.getMaker().getCompany(),
                            test.getTestRelateTime().getRecruitmentTimeLimit(),
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
    public DetailTestResponse detailTest(final UUID id){
        Test test = testRepository.findById(id).orElseThrow(
                // 예외 처리
        );
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
                test.getSymbolImageRoot()
        );
    }
}
