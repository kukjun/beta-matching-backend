package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ApproveException;
import io.wisoft.testermatchingplatform.handler.exception.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplyInformationTest {

    private ApplyInformation normalApplyInformation;
    private ApplyInformation wriedApplyInformation;

    @BeforeEach
    public void createNormalApplyInformation() {
        Mission mission = mock(Mission.class);
        Tester tester = mock(Tester.class);
        when(mission.getMaker()).thenReturn(mock(Maker.class));

        normalApplyInformation = ApplyInformation.newInstance(
                mission,
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

    @Test
    @DisplayName("신청자 선정 성공 테스트")
    public void approveApplySuccessTest() throws Exception {
        //given
        //when
        when(normalApplyInformation.getMission().getStatus()).thenReturn(MissionStatus.APPROVE);
        normalApplyInformation.applyApprove();

        //then
        assertEquals(ApplyInformationStatus.APPROVE_SUCCESS, normalApplyInformation.getStatus());
    }

    @Test
    @DisplayName("신청자 선정 실패 테스트")
    public void approveApplyFailTest() throws Exception {
        //given
        //when
        try (MockedStatic<MissionStatus> testStatusMockedStatic = mockStatic(MissionStatus.class)) {
            testStatusMockedStatic.when(() -> MissionStatus.refreshStatus(normalApplyInformation.getMission().getMissionDate()))
                    .thenReturn(MissionStatus.APPLY);
            //then
            assertThrows(ApproveException.class, () -> normalApplyInformation.applyApprove());
        }
    }

    @Test
    @DisplayName("신청자 거절 성공 테스트")
    public void rejectApplySuccessTest() throws Exception {
        //given
        //when
        when(normalApplyInformation.getMission().getStatus()).thenReturn(MissionStatus.APPROVE);
        normalApplyInformation.applyReject();
        //then
        assertEquals(ApplyInformationStatus.APPROVE_FAIL, normalApplyInformation.getStatus());
    }

    // Mock 객체 생성이 안되는 문제 - Trouble Shooting
//    @Test
//    @DisplayName("신청자 거절 성공 테스트 - 지연 테스트")
//    public void rejectApplySuccessTest_wait() throws Exception {
//        //given
//        //when
//
//        try (MockedStatic<TestStatus> testStatusMockedStatic = mockStatic(TestStatus.class)) {
//            testStatusMockedStatic.when(() -> TestStatus.refreshStatus(normalApplyInformation.getTest().getTestDate()))
//                    .thenReturn(TestStatus.PROGRESS);
//
//            when(normalApplyInformation.getTest().getMaker()).thenReturn(mock(Maker.class));
//
//        }
//        //then
//        assertEquals(ApplyInformationStatus.APPROVE_FAIL, normalApplyInformation.getStatus());
//    }

    @Test
    @DisplayName("신청자 거절 실패 테스트")
    public void rejectApplyFailTest() throws Exception {
        //given
        //when
        try (MockedStatic<MissionStatus> testStatusMockedStatic = mockStatic(MissionStatus.class)) {
            testStatusMockedStatic.when(() -> MissionStatus.refreshStatus(normalApplyInformation.getMission().getMissionDate()))
                    .thenReturn(MissionStatus.APPLY);

            when(normalApplyInformation.getMission().getMaker())
                    .thenReturn(mock(Maker.class));

            assertThrows(ApproveException.class, () -> normalApplyInformation.applyReject());
        }
    }


    @Test
    @DisplayName("수행자 완료 성공 테스트")
    public void executePerformerSuccessTest() throws Exception {
        //given
        //when
        when(normalApplyInformation.getMission().getStatus()).thenReturn(MissionStatus.PROGRESS);
        normalApplyInformation.executeApprove();
        //then
        assertEquals(ApplyInformationStatus.EXECUTE_SUCCESS, normalApplyInformation.getStatus());
    }


    @Test
    @DisplayName("수행자 완료 실패 테스트")
    public void executePerformerFailTest() throws Exception {
        //given
        //when
        try (MockedStatic<MissionStatus> testStatusMockedStatic = mockStatic(MissionStatus.class)) {
            testStatusMockedStatic.when(() -> MissionStatus.refreshStatus(normalApplyInformation.getMission().getMissionDate()))
                    .thenReturn(MissionStatus.APPLY);
            //then
            assertThrows(ExecutionException.class, () -> normalApplyInformation.executeApprove());
        }
    }


}