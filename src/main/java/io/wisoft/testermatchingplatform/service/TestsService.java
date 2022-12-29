package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.domain.Tests;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.repository.TestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//    @Transactional
//    public UpdateTestEncludeImageResponse updateTest(UUID testId, UpdateTestIncludeImageRequest request) {
//        Tests test = testsRepository.findById(testId);
//    }

}
