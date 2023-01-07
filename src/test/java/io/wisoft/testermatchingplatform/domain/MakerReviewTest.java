package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.MissionStatusMismatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MakerReviewTest {

    @Test
    public void makerReviewCreateSuccessTest() throws Exception {
        //given
        //when
        int starPoint = 5;
        String comment = "좋았습니다.";
        ApplyInformation mock = mock(ApplyInformation.class);
        when(mock.currentMissionStatus()).thenReturn(MissionStatus.COMPLETE);
        MakerReview normalMakerReview = MakerReview.newInstance(
                mock,
                starPoint,
                comment
        );

        //then
        assertEquals(starPoint, normalMakerReview.getStarPoint());
        assertEquals(comment, normalMakerReview.getComment());

    }

    @Test
    public void makerReviewCreateFailTest() throws Exception {
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
                () -> MakerReview.newInstance(mockApplyInformation, starPoint, comment)
        );

    }



}