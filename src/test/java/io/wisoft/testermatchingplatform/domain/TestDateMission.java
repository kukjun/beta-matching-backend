package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.MissionDateSequenceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestDateMission {


    @Test
    @DisplayName("TestDate 생성 성공 테스트")
    public void createTestDateSuccessTest() {
        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(5L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(10L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(20L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(30L);
        MissionDate missionDate = MissionDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );

        assertEquals(recruitmentTimeStart, missionDate.getRecruitmentTimeStart());
        assertEquals(recruitmentTimeEnd, missionDate.getRecruitmentTimeEnd());
        assertEquals(durationTimeStart, missionDate.getDurationTimeStart());
        assertEquals(durationTimeEnd, missionDate.getDurationTimeEnd());
    }

    @Test
    @DisplayName("TestDate 생성 실패 테스트 - 시간 순서가 맞지 않음")
    public void createTestDateFailTestFromTimeSequenceMismatch() {
        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(10L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(5L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(5L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(3L);
        assertThrows(
                MissionDateSequenceException.class,
                () -> MissionDate.newInstance(
                        recruitmentTimeStart,
                        recruitmentTimeEnd,
                        durationTimeStart,
                        durationTimeEnd
                )
        );
    }

    @Test
    @DisplayName("TestDate 생성 실패 테스트 - 신청 시작 시간이 현재보다 이전")
    public void createTestDateFailTestFromRecruitmentTimeStartMismatch() {
        LocalDate recruitmentTimeStart = LocalDate.now().minusDays(5L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(10L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(20L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(30L);

        assertThrows(
                MissionDateSequenceException.class,
                () -> MissionDate.newInstance(
                        recruitmentTimeStart,
                        recruitmentTimeEnd,
                        durationTimeStart,
                        durationTimeEnd
                )
        );
    }

    @Test
    @DisplayName("테스트의 남은 기간 표기 테스트")
    public void remainTimeTest() throws Exception {
        //given
        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(5L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(10L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(20L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(30L);
        MissionDate missionDate = MissionDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );

        assertEquals(recruitmentTimeStart, missionDate.getRecruitmentTimeStart());
        assertEquals(recruitmentTimeEnd, missionDate.getRecruitmentTimeEnd());
        assertEquals(durationTimeStart, missionDate.getDurationTimeStart());
        assertEquals(durationTimeEnd, missionDate.getDurationTimeEnd());

        //when
        long remainDay = missionDate.remainApplyTime();
        long expectedDay = recruitmentTimeEnd.toEpochDay() - LocalDate.now().toEpochDay();
        System.out.println("expectedDay = " + expectedDay);
        //then
        assertEquals(expectedDay, remainDay);
    }


}