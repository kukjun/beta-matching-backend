package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ReviewException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MakerReviewTest {

    @Test
    public void makerReviewCreateSuccessTest() throws Exception {
        //given
        //when
        int starPoint = 5;
        String comment = "좋았습니다.";
        ApplyInformation mock = mock(ApplyInformation.class);
        when(mock.currentTestStatus()).thenReturn(TestStatus.COMPLETE);
        MakerReview normalMakerReview = MakerReview.newInstance(
                mock,
                starPoint,
                comment
        );

        //then
        assertEquals(starPoint, normalMakerReview.getStarPoint());
        assertEquals(comment, normalMakerReview.comment);

    }

    @Test
    public void makerReviewCreateFailTest() throws Exception {
        //given
        //when
        int starPoint = 5;
        String comment = "좋았습니다.";
        ApplyInformation mock = mock(ApplyInformation.class);
        when(mock.currentTestStatus()).thenReturn(TestStatus.APPROVE);

        assertThrows(
                ReviewException.class,
                () -> MakerReview.newInstance(
                        mock,
                        starPoint,
                        comment
                )
        );

    }



}