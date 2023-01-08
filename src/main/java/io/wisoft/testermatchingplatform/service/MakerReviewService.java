package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.MakerReview;
import io.wisoft.testermatchingplatform.handler.exception.service.ApplyInformationNotFoundException;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MakerReviewRepository;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerReviewListRequest;
import io.wisoft.testermatchingplatform.web.dto.response.CreateMakerReviewListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MakerReviewService {
    private final MakerReviewRepository makerReviewRepository;
    private final ApplyInformationRepository applyInformationRepository;

    @Transactional
    public CreateMakerReviewListResponse createMakerReview(UUID makerId, CreateMakerReviewListRequest request) {
        ApplyInformation applyInformation = applyInformationRepository.findById(request.getApplyInformationId()).orElseThrow(
                () -> new ApplyInformationNotFoundException("id: " + request.getApplyInformationId() + "not found")
        );

        MakerReview makerReview = request.toMakerReview(applyInformation);
        MakerReview storedMakerReview = makerReviewRepository.save(makerReview);

        CreateMakerReviewListResponse response = CreateMakerReviewListResponse.fromMakerReview(storedMakerReview);

        return response;
    }
}
