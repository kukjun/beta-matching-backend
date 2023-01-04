package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.TesterReview;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.TesterReviewRepository;
import io.wisoft.testermatchingplatform.web.dto.TesterReviewDTO;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterReviewListRequest;
import io.wisoft.testermatchingplatform.web.dto.response.CreateTesterReviewListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TesterReviewService {

    private final TesterReviewRepository testerReviewRepository;
    private final ApplyInformationRepository applyInformationRepository;

    @Transactional
    public CreateTesterReviewListResponse createTesterReview(UUID makerId, CreateTesterReviewListRequest request) {
        List<TesterReviewDTO> requestDTOList = request.getTesterReviewDTOList();
        List<UUID> responseDTOList = new ArrayList<>();

        for (TesterReviewDTO testerReviewDTO : requestDTOList) {
            ApplyInformation applyInformation = applyInformationRepository.findById(testerReviewDTO.getApplyInformationId());
            TesterReview testerReview = testerReviewDTO.toTesterReview(applyInformation);
            UUID saveId = testerReviewRepository.save(testerReview);
            responseDTOList.add(saveId);
        }
        CreateTesterReviewListResponse response = CreateTesterReviewListResponse.fromUUIDList(responseDTOList);
        return response;
    }

}
