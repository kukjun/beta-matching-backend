package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.MakerReview;
import io.wisoft.testermatchingplatform.handler.exception.service.ApplyInformationNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.service.MakerReviewOverlapException;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MakerReviewRepository;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerReviewRequest;
import io.wisoft.testermatchingplatform.web.dto.response.CreateMakerReviewResponse;
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
    public CreateMakerReviewResponse createMakerReview(UUID applyInformationId, CreateMakerReviewRequest request) {
        System.out.println("request.getStarPoint() = " + request.getStarPoint());
        System.out.println("request.getComment() = " + request.getComment());
        // 같은 Apply로 만들어진 Maker Review가 있는지 확인
        if (makerReviewRepository.existsByApplyInformation_Id(applyInformationId)) {
            throw new MakerReviewOverlapException("applyInformation Id: " + applyInformationId + " is existed");
        }
        ApplyInformation applyInformation = applyInformationRepository.findById(applyInformationId).orElseThrow(
                () -> new ApplyInformationNotFoundException("id: " + applyInformationId + "not found")
        );

        MakerReview makerReview = request.toMakerReview(applyInformation);
        MakerReview storedMakerReview = makerReviewRepository.save(makerReview);

        CreateMakerReviewResponse response = CreateMakerReviewResponse.fromMakerReview(storedMakerReview);

        return response;
    }
}
