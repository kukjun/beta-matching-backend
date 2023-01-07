package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.NotNaturalNumberException;
import org.junit.jupiter.api.*;

import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MissionTest {


    private Mission normalMission;



    @BeforeEach
    public void createNormalTest() {
        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        String imageURL = "ImageURLCheck";
        long point = 100L;
        int limitApply = 100;
        LocalDate recruitmentTimeStart = LocalDate.now();
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(20L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(30L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(50L);
        Maker mockMaker = mock(Maker.class);

        normalMission = Mission.newInstance(
                title,
                content,
                imageURL,
                point,
                limitApply,
                mockMaker,
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
    }


    @Test
    @DisplayName("테스트 생성 테스트 - 성공")
    public void createTestSuccessTest() {
        // given
        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        // when
        // then
        assertEquals(title, normalMission.getTitle());
        assertEquals(content, normalMission.getContent());
    }

    @Test
    @DisplayName("이미지를 포함하는 Mission 업데이트 테스트 - 성공")
    public void updateIncludeImageMissionSuccessTest() throws Exception {
        //given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        String updateURL = "updateURL";
        long updateReward = 500L;
        int updateLimitCount = 30;
        LocalDate updateRecruitmentTimeStart = LocalDate.now().plusDays(1L);
        LocalDate updateRecruitmentTimeEnd = LocalDate.now().plusDays(5L);
        LocalDate updateDurationTimeStart = LocalDate.now().plusDays(10L);
        LocalDate updateDurationTimeEnd = LocalDate.now().plusDays(15L);

        //when
        normalMission.updateIncludeImageMission(
                updateTitle,
                updateContent,
                updateURL,
                updateReward,
                updateLimitCount,
                updateRecruitmentTimeStart,
                updateRecruitmentTimeEnd,
                updateDurationTimeStart,
                updateDurationTimeEnd
        );

        //then
        assertEquals(updateTitle, normalMission.getTitle());
        assertEquals(updateContent, normalMission.getContent());
        assertEquals(updateReward, normalMission.getReward());
    }

    @Test
    @DisplayName("이미지를 제외하는 Mission 업데이트 테스트 - 성공")
    public void updateExceptImageMissionSuccessTest() throws Exception {
        //given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        long updateReward = 500L;
        int updateLimitCount = 30;
        LocalDate updateRecruitmentTimeStart = LocalDate.now().plusDays(1L);
        LocalDate updateRecruitmentTimeEnd = LocalDate.now().plusDays(5L);
        LocalDate updateDurationTimeStart = LocalDate.now().plusDays(10L);
        LocalDate updateDurationTimeEnd = LocalDate.now().plusDays(15L);

        //when
        normalMission.updateExceptImageMission(
                updateTitle,
                updateContent,
                updateReward,
                updateLimitCount,
                updateRecruitmentTimeStart,
                updateRecruitmentTimeEnd,
                updateDurationTimeStart,
                updateDurationTimeEnd
        );

        //then
        assertEquals(updateTitle, normalMission.getTitle());
        assertEquals(updateContent, normalMission.getContent());
        assertEquals(updateReward, normalMission.getReward());
    }

    @Test
    @DisplayName("테스트 생성 테스트 - 실패")
    public void CreateMissionFailTest() throws Exception {
        //given
        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        String imageURL = "ImageURLCheck";
        long point = 100L;
        int limitApply = 0;
        LocalDate recruitmentTimeStart = LocalDate.now();
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(20L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(30L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(50L);
        Maker mockMaker = mock(Maker.class);

        //when
        //then
        assertThrows(

                NotNaturalNumberException.class,
                () -> Mission.newInstance(
                        title,
                        content,
                        imageURL,
                        point,
                        limitApply,
                        mockMaker,
                        recruitmentTimeStart,
                        recruitmentTimeEnd,
                        durationTimeStart,
                        durationTimeEnd
                )
        );
    }

    @Test
    @DisplayName("이미지를 포함하는 Mission 업데이트 테스트 - 실패")
    public void updateIncludeImageMissionFailTest() throws Exception {
        //given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        String updateURL = "updateURL";
        long updateReward = 500L;
        int updateLimitCount = 0;
        LocalDate updateRecruitmentTimeStart = LocalDate.now().plusDays(1L);
        LocalDate updateRecruitmentTimeEnd = LocalDate.now().plusDays(5L);
        LocalDate updateDurationTimeStart = LocalDate.now().plusDays(10L);
        LocalDate updateDurationTimeEnd = LocalDate.now().plusDays(15L);

        //when
        //then
        assertThrows(NotNaturalNumberException.class,
                () -> normalMission.updateIncludeImageMission(
                        updateTitle,
                        updateContent,
                        updateURL,
                        updateReward,
                        updateLimitCount,
                        updateRecruitmentTimeStart,
                        updateRecruitmentTimeEnd,
                        updateDurationTimeStart,
                        updateDurationTimeEnd
                )
        );
    }

    @Test
    @DisplayName("이미지를 제외하는 Mission 업데이트 테스트 - 실패")
    public void updateExceptImageMissionFailTest() throws Exception {
        //given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        long updateReward = 500L;
        int updateLimitCount = 0;
        LocalDate updateRecruitmentTimeStart = LocalDate.now().plusDays(1L);
        LocalDate updateRecruitmentTimeEnd = LocalDate.now().plusDays(5L);
        LocalDate updateDurationTimeStart = LocalDate.now().plusDays(10L);
        LocalDate updateDurationTimeEnd = LocalDate.now().plusDays(15L);

        //when
        //then
        assertThrows(NotNaturalNumberException.class,
                () -> normalMission.updateExceptImageMission(
                        updateTitle,
                        updateContent,
                        updateReward,
                        updateLimitCount,
                        updateRecruitmentTimeStart,
                        updateRecruitmentTimeEnd,
                        updateDurationTimeStart,
                        updateDurationTimeEnd
                )
        );
    }

    @Test
    @DisplayName("남은 신청기간 조회 테스트 - 성공")
    public void remainApplyDaysSuccessTest() throws Exception {
        //given
        LocalDate currentDays = LocalDate.now();
        LocalDate updateRecruitmentTimeEnd = LocalDate.now().plusDays(20L);
        long expectedRemainDays = updateRecruitmentTimeEnd.toEpochDay() - currentDays.toEpochDay();
        //when
        long remainDays = normalMission.remainApplyDays();
        //then
        assertEquals(expectedRemainDays, remainDays);
    }



}
