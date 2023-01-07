package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.MissionStatusMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplyInformationTest {

    private ApplyInformation normalApplyInformation;
    private Mission mockMission;
    private Tester mockTester;

    @BeforeEach
    public void createNormalApplyInformation() {
        mockMission = mock(Mission.class);
        mockTester = mock(Tester.class);
        when(mockMission.getMaker()).thenReturn(mock(Maker.class));

        normalApplyInformation = ApplyInformation.newInstance(
                mockMission,
                mockTester
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
    @DisplayName("신청자 거절 성공 테스트")
    public void rejectApplySuccessTest() throws Exception {
        //given
        //when
        when(mockMission.getStatus()).thenReturn(MissionStatus.APPROVE);
        normalApplyInformation.applyReject();
        //then
        assertEquals(ApplyInformationStatus.APPROVE_FAIL, normalApplyInformation.getStatus());
    }

    @Test
    @DisplayName("신청자 거절 실패 테스트")
    public void rejectApplyFailTest() throws Exception {
        //given
        //when
        when(mockMission.getStatus()).thenReturn(MissionStatus.COMPLETE);
        assertThrows(MissionStatusMismatchException.class, () -> normalApplyInformation.applyReject());
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
        when(mockMission.getStatus()).thenReturn(MissionStatus.COMPLETE);
        assertThrows(MissionStatusMismatchException.class, () -> normalApplyInformation.applyReject());
    }


}