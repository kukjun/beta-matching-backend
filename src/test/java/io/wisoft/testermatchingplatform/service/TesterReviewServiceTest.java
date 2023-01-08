package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.*;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.TesterReviewRepository;
import io.wisoft.testermatchingplatform.web.dto.TesterReviewDTO;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterReviewListRequest;
import io.wisoft.testermatchingplatform.web.dto.response.CreateTesterReviewListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TesterReviewServiceTest {

    @Mock
    private TesterReviewRepository testerReviewRepository;
    @Mock
    private ApplyInformationRepository applyInformationRepository;

    private TesterReviewService testerReviewService;

    @BeforeEach
    public void prepareTest() {
        testerReviewService = new TesterReviewService(testerReviewRepository, applyInformationRepository);
    }

    @Test
    @DisplayName("Tester 리뷰 생성 테스트 - 성공")
    public void createTesterReviewSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.randomUUID();

        List<TesterReviewDTO> testerReviewDTOList = new ArrayList<>();
        UUID applyInformationId = UUID.randomUUID();
        testerReviewDTOList.add(
                TesterReviewDTO.newInstance(
                        applyInformationId,
                        "testerNickname",
                        3,
                        "test comment",
                        ApplyInformationStatus.EXECUTE_SUCCESS.toString()
                )
        );
        CreateTesterReviewListRequest request = CreateTesterReviewListRequest.newInstance(testerReviewDTOList);

        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
        when(applyInformationRepository.findById(applyInformationId)).thenReturn(Optional.ofNullable(mockApplyInformation));


        UUID expectedTesterReviewId = UUID.randomUUID();
        TesterReview mockTesterReview = mock(TesterReview.class);
        when(mockTesterReview.getId()).thenReturn(expectedTesterReviewId);
        when(testerReviewRepository.save(any(TesterReview.class))).thenReturn(mockTesterReview);

        //when
        CreateTesterReviewListResponse response = testerReviewService.createTesterReview(makerId, request);

        //then
        assertEquals(expectedTesterReviewId, response.getTesterReviewIdDTOList().get(0));

    }


}