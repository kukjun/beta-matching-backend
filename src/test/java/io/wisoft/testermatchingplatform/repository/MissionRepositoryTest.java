package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.domain.Mission;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.event.NamingExceptionEvent;
import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest 이거하면 TestData를 넣을 수 없음.
@SpringBootTest
@Transactional
class MissionRepositoryTest {
    @Autowired
    MissionRepository missionRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Mission 저장 테스트 - 성공")
    public void findByIdSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        Maker maker = em.find(Maker.class, makerId);

        String title = "Create Mission Create";
        String content = "Create Mission Content";
        String testURL = "Create testURL";
        long point = 100L;
        int limitPerformer = 20;

        Mission mission = Mission.newInstance(
                title,
                content,
                testURL,
                point,
                limitPerformer,
                maker,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(20)
        );
        //when
        Mission storedMission = missionRepository.save(mission);

        System.out.println(storedMission.getStatus());

        //then
        assertEquals(mission, storedMission);

    }

    @Test
    @DisplayName("Mission Id로 조회 - 성공")
    public void saveMissionSuccessTest() throws Exception {
        //given
        String title = "[공공공간] 온라인 베타 테스터를 모집합니다";
        String testURL = "test5.png";

        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c0");
        //when
        Mission mission = missionRepository.findById(missionId).get();

        //then
        assertEquals(title, mission.getTitle());
        assertEquals(testURL, mission.getImageURL());
    }

    @Test
    @DisplayName("모든 Mission 조회 Test - 성공")
    public void findAllMissionSuccessTest() throws Exception {
        //given
        int expectSize = 7;

        //when
        List<Mission> all = missionRepository.findAll();

        //then
        assertEquals(expectSize, all.size());
    }

    @Test
    @DisplayName("현재 신청 기간의 ApplyMissionTest 조회 - 성공")
    public void findApplyMissionsSuccessTest() throws Exception {
        //given
        LocalDate currentDate = LocalDate.now();
        int expectedSize = 3;

        //when
        List<Mission> applyMissions = missionRepository.findApplyMissions(currentDate);

        //then
        assertEquals(expectedSize, applyMissions.size());
    }

    @Test
    @DisplayName("현재 신청 기간 내에서 본인이 신청한 mission을 제외하고 조회하는 Test")
    public void findApplyMissionsExceptTesterIdSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        LocalDate currentDate = LocalDate.parse("2023-01-02");

        //when
        List<Mission> appliedMissions = missionRepository.findApplyMissionsExceptTesterId(testerId, currentDate);

        //then
        assertEquals(2, appliedMissions.size());
    }

    @Test
    @DisplayName("현재 신청 기간 내에서 본인이 신청한 mission을 제외하고 조회, 순서는 생성된 순서대로 확인하는 Test")
    public void findApplyMissionsExceptTesterIdByCreatedSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        LocalDate currentDate = LocalDate.parse("2023-01-02");
        LocalDate expectFirstDate = LocalDateTime.parse("2022-12-01T15:00").toLocalDate();
        LocalDate expectSecondDate = LocalDateTime.parse("2023-01-01T15:00").toLocalDate();

        //when
        List<Mission> appliedMissions = missionRepository.findApplyMissionsExceptTesterIdByCreated(testerId, currentDate);

        Mission firstMission = appliedMissions.get(0);
        Mission secondMission = appliedMissions.get(1);
        //then
        assertEquals(2, appliedMissions.size());
        assertEquals(expectFirstDate, firstMission.getCreateDateTime().toLocalDate());
        assertEquals(expectSecondDate, secondMission.getCreateDateTime().toLocalDate());

    }

    @Test
    @DisplayName("현재 신청 기간 내에서 본인이 신청한 mission을 제외하고 조회, 순서는 신청인원이 많은 순서대로 확인하는 Test")
    public void findApplyMissionsExceptTesterIdByPopularSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        LocalDate currentDate = LocalDate.parse("2023-01-02");
        int expectedFirstApplySize = 1;
        int expectedSecondApplySize = 0;

        //when
        List<Mission> appliedMissions = missionRepository.findApplyMissionsExceptTesterIdByPopular(testerId, currentDate);
        Mission firstMission = appliedMissions.get(0);
        Mission secondMission = appliedMissions.get(1);

        //then
        assertEquals(2, appliedMissions.size());
        assertEquals(expectedFirstApplySize, firstMission.getApplyInformationList().size());
        assertEquals(expectedSecondApplySize, secondMission.getApplyInformationList().size());

    }

    @Test
    @DisplayName("현재 신청 기간 내에서 본인이 신청한 mission을 제외하고 조회, 순서는 마감시간이 짧은 순서대로 확인하는 Test")
    public void findApplyMissionsExceptTesterIdByDeadlineSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        LocalDate currentDate = LocalDate.parse("2023-01-02");
        long expectedFirstMissionDeadline = 30L;
        long expectedSecondMissionDeadline = 34L;

        //when
        List<Mission> appliedMissions = missionRepository.findApplyMissionsExceptTesterIdByDeadLine(testerId, currentDate);
        Mission firstMission = appliedMissions.get(0);
        Mission secondMission = appliedMissions.get(1);

        //then
        assertEquals(2, appliedMissions.size());
        assertEquals(expectedFirstMissionDeadline, firstMission.remainApplyDays(currentDate));
        assertEquals(expectedSecondMissionDeadline, secondMission.remainApplyDays(currentDate));
    }

    @Test
    @DisplayName("Maker가 생성한 Mission 조회 - 성공")
    public void findAppliedMissionsByMakerIdSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        int expectedSize = 7;

        //when
        List<Mission> missionList = missionRepository.findAppliedMissionsByMakerId(makerId);

        //then
        assertEquals(expectedSize, missionList.size());
    }

    @Test
    @DisplayName("현재 신청부터, 수행중인 모든 Mission의 개수 조회 - 성공")
    public void findProgressMissionCountSuccessTest() throws Exception {
        //given
        long expectedProgressMissionCount = 5L;
        LocalDate currentDate = LocalDate.parse("2023-01-02");
        //when
        long progressMissionCount = missionRepository.findProgressMission(currentDate);
        //then
        assertEquals(expectedProgressMissionCount, progressMissionCount);
    }

    @Test
    @DisplayName("현재 기간이 전부 지나 종료된 Mission의 개수 조회 - 성공")
    public void findCompleteMissionCountSuccessTest() throws Exception {
        //given
        long expectedCompleteMissionCount = 1L;
        LocalDate currentDate = LocalDate.parse("2023-01-02");
        //when
        long completeMissionCount = missionRepository.findCompleteMission(currentDate);
        //then
        assertEquals(expectedCompleteMissionCount, completeMissionCount);
    }

    @Test
    @DisplayName("가장 유명한 Mission 4개 인기 순으로 조회 - 성공")
    public void findPopularTop4Mission() throws Exception {
        //given
        LocalDate currentDate = LocalDate.parse("2023-01-02");
        int expectedFirstMissionSize = 4;
        int expectedSecondMissionSize = 1;
        int expectedThirdMissionSize = 0;

        //when
        List<Mission> appliedMissions = missionRepository.findPopularTop4Mission(currentDate);

        //then
        Mission firstMission = appliedMissions.get(0);
        Mission secondMission = appliedMissions.get(1);
        Mission thirdMission = appliedMissions.get(2);

        //then
        assertEquals(3, appliedMissions.size());
        assertEquals(expectedFirstMissionSize,firstMission.getApplyInformationList().size());
        assertEquals(expectedSecondMissionSize,secondMission.getApplyInformationList().size());
        assertEquals(expectedThirdMissionSize, thirdMission.getApplyInformationList().size());

    }

    @Test
    @DisplayName("가장 마감기간이 적게 남은 Mission 4개 마감일자가 가까운 순으로 정렬")
    public void findDeadlineTop4MissionTest() throws Exception {
        //given
        LocalDate currentDate = LocalDate.parse("2023-01-02");
        long expectFirstMissionRemainDays = 30L;
        long expectSecondMissionRemainDays = 30L;
        long expectThirdMissionRemainDays = 34L;

        //when
        List<Mission> appliedMissions = missionRepository.findPopularTop4Mission(currentDate);
        Mission firstMission = appliedMissions.get(0);
        Mission secondMission = appliedMissions.get(1);
        Mission thirdMission = appliedMissions.get(2);

        //then
        assertEquals(3, appliedMissions.size());
        assertEquals(expectFirstMissionRemainDays , firstMission.remainApplyDays(currentDate));
        assertEquals(expectSecondMissionRemainDays , secondMission.remainApplyDays(currentDate));
        assertEquals(expectThirdMissionRemainDays , thirdMission.remainApplyDays(currentDate));

    }


}