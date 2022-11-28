package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.TestDateSequenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestDateTest {


    @Test
    @DisplayName("TestDate 생성 성공 테스트")
    public void createTestDateSuccessTest() {
        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(5L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(10L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(20L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(30L);
        TestDate testDate = TestDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );

        assertEquals(recruitmentTimeStart, testDate.getRecruitmentTimeStart());
        assertEquals(recruitmentTimeEnd, testDate.getRecruitmentTimeEnd());
        assertEquals(durationTimeStart, testDate.getDurationTimeStart());
        assertEquals(durationTimeEnd, testDate.getDurationTimeEnd());
    }

    @Test
    @DisplayName("TestDate 생성 실패 테스트 - 시간 순서가 맞지 않음")
    public void createTestDateFailTestFromTimeSequenceMismatch() {
        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(10L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(5L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(5L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(3L);
        assertThrows(
                TestDateSequenceException.class,
                () -> TestDate.newInstance(
                        recruitmentTimeStart,
                        recruitmentTimeEnd,
                        durationTimeStart,
                        durationTimeEnd
                )
        );
    }

    @Test
    @DisplayName("TestDate 생성 실패 테스트 - 신청 시작 시간이 현재보다 더 빠름")
    public void createTestDateFailTestFromRecruitmentTimeStartMismatch() {
        LocalDate recruitmentTimeStart = LocalDate.now().minusDays(5L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(10L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(20L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(30L);

        assertThrows(
                TestDateSequenceException.class,
                () -> TestDate.newInstance(
                        recruitmentTimeStart,
                        recruitmentTimeEnd,
                        durationTimeStart,
                        durationTimeEnd
                )
        );
    }

}