package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.MissionStatusMismatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TesterReviewTest {

    @Test
    @DisplayName("테스터 리뷰 생성 성공 테스트")
    public void testerReviewCreateSuccessTest() throws Exception {
        //given
        //when
        int starPoint = 5;
        String comment = "좋았습니다.";
        ApplyInformation mock = mock(ApplyInformation.class);
        when(mock.currentMissionStatus()).thenReturn(MissionStatus.COMPLETE);
        TesterReview normalTesterReview = TesterReview.newInstance(
                mock,
                starPoint,
                comment
        );

        //then
        assertEquals(starPoint, normalTesterReview.getStarPoint());
        assertEquals(comment, normalTesterReview.getComment());
    }

    @Test
    @DisplayName("테스터 리뷰 생성 실패 테스트 - 테스트 상태가 완료 상태가 아님")
    public void testerReviewCreateFailTest() throws Exception {
        //given
        //when
        int starPoint = 5;
        String comment = "좋았습니다.";
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);

        doThrow(MissionStatusMismatchException.class)
                .when(mockApplyInformation)
                .isMissionStatsMatch(MissionStatus.COMPLETE);

        assertThrows(
                MissionStatusMismatchException.class,
                () -> TesterReview.newInstance(mockApplyInformation, starPoint, comment)
        );


    }


}