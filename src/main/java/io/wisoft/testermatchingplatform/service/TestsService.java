package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.domain.TestStatus;
import io.wisoft.testermatchingplatform.domain.Tests;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.repository.TestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestsService {

    private final TestsRepository testsRepository;
    private final MakerRepository makerRepository;

    @Transactional
    public CreateTestResponse createTest(UUID makerId, CreateTestRequest request) {
        Maker maker = makerRepository.findById(makerId);
        String imageFileURL = FileHandler.saveTestImageFileData(request.getImage());
        Tests test = request.toTest(maker, imageFileURL);

        UUID saveId = testsRepository.save(test);

        CreateTestResponse response = CreateTestResponse.fromTest(test);

        return response;
    }

    @Transactional
    public UpdateTestIncludeImageResponse updateTest(UUID testId, UpdateTestIncludeImageRequest request) {
        Tests test = testsRepository.findById(testId);
        String imageFileURL = FileHandler.saveTestImageFileData(request.getImage());
        // 이미지 삭제 로직 추가하기

        test.updateIncludeImageTest(
                request.getTitle(),
                request.getContent(),
                imageFileURL,
                request.getReward(),
                request.getLimitPerformer(),
                LocalDate.parse(request.getRecruitmentTimeStart(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getRecruitmentTimeLimit(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getDurationTimeStart(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getDurationTimeLimit(), DateTimeFormatter.ISO_LOCAL_DATE)
        );

        UpdateTestIncludeImageResponse response = UpdateTestIncludeImageResponse.fromTest(test);
        return response;
    }

    @Transactional
    public UpdateTestExceptImageResponse updateExceptImageTest(UUID testId, UpdateTestExceptImageRequest request) {
        Tests test = testsRepository.findById(testId);

        test.updateExceptImageTest(
                request.getTitle(),
                request.getContent(),
                request.getReward(),
                request.getLimitPerformer(),
                LocalDate.parse(request.getRecruitmentTimeStart(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getRecruitmentTimeLimit(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getDurationTimeStart(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getDurationTimeLimit(), DateTimeFormatter.ISO_LOCAL_DATE)
        );

        UpdateTestExceptImageResponse response = UpdateTestExceptImageResponse.fromTest(test);
        return response;
    }

    public DetailTestResponse detailTest(UUID testId) {
        Tests test = testsRepository.findById(testId);

        DetailTestResponse response = DetailTestResponse.fromTest(test);
        return response;
    }

//    public List<SimpleTestResponse> PopularTop4Tests() {
//        List<Tests> popularTop4Tests = testsRepository.findPopularTop4();
//    }

    public SimpleTestListResponse applyTestList() {
        List<Tests> list = testsRepository.findApplyTests();
        SimpleTestListResponse response = SimpleTestListResponse.fromTestList(list);
        return response;
    }

    public ApplyTestListFromTesterResponse applyTestListFromTester(UUID testerId) {
        List<Tests> list = testsRepository.findAppliedTestsByTesterId(testerId);
        List<AppliedTestDTO> appliedTestDTOList = new ArrayList<>();
        List<ApprovedTestDTO> approvedTestDTOList = new ArrayList<>();
        List<CompletedTestDTO> completedTestDTOList = new ArrayList<>();

        for (Tests test : list) {
            TestStatus status = test.getStatus();
            switch (status) {
                case APPLY:
                    appliedTestDTOList.add(AppliedTestDTO.fromTest(test));
                    break;
                case APPROVE:
                case PROGRESS:
                    approvedTestDTOList.add(ApprovedTestDTO.fromTest(test));
                    break;
                case COMPLETE:
                    completedTestDTOList.add(CompletedTestDTO.fromTest(test));
            }
        }

        ApplyTestListFromTesterResponse response = ApplyTestListFromTesterResponse.fromDTO(
                appliedTestDTOList,
                approvedTestDTOList,
                completedTestDTOList
        );

        return response;
    }



}
