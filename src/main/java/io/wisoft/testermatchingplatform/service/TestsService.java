package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.*;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.repository.TestsRepository;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTestRequest;
import io.wisoft.testermatchingplatform.web.dto.request.UpdateTestExceptImageRequest;
import io.wisoft.testermatchingplatform.web.dto.request.UpdateTestIncludeImageRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestsService {

    private final TestsRepository testsRepository;
    private final MakerRepository makerRepository;
    private final ApplyInformationRepository applyInformationRepository;

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
    public UpdateTestIncludeImageResponse updateIncludeImageTest(UUID testId, UpdateTestIncludeImageRequest request) {
        Tests test = testsRepository.findById(testId);
        FileHandler.removeTestImage(test.getImageURL());
        String imageFileURL = FileHandler.saveTestImageFileData(request.getImage());

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

    public SimpleTestListResponse applyTestList(UUID testerId) {
        List<Tests> list = testsRepository.findApplyTestsExceptTesterId(testerId);
        SimpleTestListResponse response = SimpleTestListResponse.fromTestList(list);
        return response;
    }

    public ApplyTestListFromTesterResponse applyTestListFromTester(UUID testerId) {
        List<ApplyInformation> applyInformationList = applyInformationRepository.findByTesterId(testerId);

        ApplyTestListFromTesterResponse response = ApplyTestListFromTesterResponse.fromApplyInformationList(
                applyInformationList
        );

        return response;
    }

    public SimpleTestListResponse applyTestListByCreated(UUID testerId) {
        List<Tests> testList = testsRepository.findApplyTestsExceptTesterIdByCreated(testerId);
        SimpleTestListResponse response = SimpleTestListResponse.fromTestList(testList);

        return response;
    }

    public SimpleTestListResponse applyTestListByDeadLine(UUID testerId) {
        List<Tests> testList = testsRepository.findApplyTestsExceptTesterIdByDeadLine(testerId);
        SimpleTestListResponse response = SimpleTestListResponse.fromTestList(testList);

        return response;
    }

    public SimpleTestListResponse applyTestListByPopular(UUID testerId) {
        List<Tests> testList = testsRepository.findApplyTestsExceptTesterIdByPopular(testerId);
        SimpleTestListResponse response = SimpleTestListResponse.fromTestList(testList);

        return response;
    }

    public ApplyTestListFromMakerResponse madeTestListFromMaker(UUID makerId) {
        List<Tests> list = testsRepository.findAppliedTestsByMakerId(makerId);
        ApplyTestListFromMakerResponse response = ApplyTestListFromMakerResponse.fromTestList(list);

        return response;

    }






}
