package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ApproveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

class ApplyInformationTest {

    private ApplyInformation normalApplyInformation;
    private ApplyInformation wriedApplyInformation;

    @BeforeEach
    public void createNormalApplyInformation() {
        Tests tests = mock(Tests.class);
        Tester tester = mock(Tester.class);

        normalApplyInformation = ApplyInformation.newInstance(
                tests,
                tester
        );
    }

    @Test
    @DisplayName("신청정보 생성 성공 테스트")
    public void createApplyInformationSuccessTest() throws Exception {
        //given
        //when
        //then
        assertEquals(ApplyInformationStatus.APPLY, normalApplyInformation.getStatus());
    }

    // 신청취소 테스트 - Unit Test 불가

    @Test
    @DisplayName("신청자 수락 성공 테스트")
    public void approveApplySuccessTest() throws Exception {
        //given
        //when
        try (MockedStatic<TestStatus> testStatusMockedStatic = mockStatic(TestStatus.class)) {
            testStatusMockedStatic.when(() -> TestStatus.refreshStatus(normalApplyInformation.getTest().getTestDate()))
                    .thenReturn(TestStatus.APPROVE);
            normalApplyInformation.approve();
        }
        //then
        assertEquals(ApplyInformationStatus.APPROVE_SUCCESS, normalApplyInformation.getStatus());
    }

    @Test
    @DisplayName("신청자 수락 실패 테스트")
    public void approveApplyFailTest() throws Exception {
        //given
        //when
        try (MockedStatic<TestStatus> testStatusMockedStatic = mockStatic(TestStatus.class)) {
            testStatusMockedStatic.when(() -> TestStatus.refreshStatus(normalApplyInformation.getTest().getTestDate()))
                    .thenReturn(TestStatus.APPLY);
            //then
            assertThrows(ApproveException.class, () -> normalApplyInformation.approve());
        }
    }

}