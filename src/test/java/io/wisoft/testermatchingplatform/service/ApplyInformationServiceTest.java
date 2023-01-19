package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.*;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.repository.MissionRepository;
import io.wisoft.testermatchingplatform.repository.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.request.ChangeApplyToApproveRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePerformToCompleteRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplyInformationServiceTest {

    @Mock
    private ApplyInformationRepository applyInformationRepository;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private TesterRepository testerRepository;

    @Mock
    private MakerRepository makerRepository;

    private ApplyInformationService applyInformationService;

    @BeforeEach
    public void prepareTest() {
        applyInformationService = new ApplyInformationService(applyInformationRepository, missionRepository, testerRepository, makerRepository);
    }

    @Test
    @DisplayName("미션 신청 테스트 - 성공")
    public void applyMissionSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        Mission mockMission = mock(Mission.class);
        when(missionRepository.findById(missionId)).thenReturn(Optional.ofNullable(mockMission));

        UUID testerId = UUID.randomUUID();
        Tester mockTester = mock(Tester.class);
        when(testerRepository.findById(testerId)).thenReturn(Optional.ofNullable(mockTester));

        UUID expectedId = UUID.randomUUID();
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
        when(mockApplyInformation.getId()).thenReturn(expectedId);

        when(applyInformationRepository.save(any(ApplyInformation.class))).thenReturn(mockApplyInformation);

        //when
        ApplyMissionResponse response = applyInformationService.applyMission(testerId, missionId);

        //then
        assertEquals(expectedId, response.getApplyId());
    }

    @Test
    @DisplayName("미션 신청 취소 테스트 - 성공")
    public void cancelApplySuccessTest() throws Exception {
        //given
        UUID applyInformationId = UUID.randomUUID();
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
        when(applyInformationRepository.findById(applyInformationId)).thenReturn(Optional.ofNullable(mockApplyInformation));

        //when
        //then
        applyInformationService.cancelApply(applyInformationId);
    }

    @Test
    @DisplayName("신청인원을 선정인원으로 변경 - 성공")
    public void applyToApproveSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        Mission mockMission = mock(Mission.class);
        when(missionRepository.findById(missionId)).thenReturn(Optional.ofNullable(mockMission));

        when(applyInformationRepository.existsApplyInformationByMissionIdAndApproveTimeIsNull(missionId)).thenReturn(0L);
        Tester[] mockTesterList = new Tester[3];
        UUID[] mockTesterIdList = new UUID[3];
        UUID[] mockApplyInformationIdList = new UUID[3];

        List<ApplyInformation> mockApplyInformations = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
            mockApplyInformationIdList[i] = UUID.randomUUID();
            lenient().when(mockApplyInformation.getId()).thenReturn(mockApplyInformationIdList[i]);
            mockTesterList[i] = mock(Tester.class);
            mockTesterIdList[i] = UUID.randomUUID();
            when(mockApplyInformation.getTester()).thenReturn(mockTesterList[i]);
            when(mockTesterList[i].getId()).thenReturn(mockTesterIdList[i]);
            mockApplyInformations.add(mockApplyInformation);
        }
        when(mockMission.getApplyInformationList()).thenReturn(mockApplyInformations);

        List<UUID> requestList = new ArrayList<>();
        requestList.add(mockTesterList[0].getId());

        ChangeApplyToApproveRequest request = ChangeApplyToApproveRequest.newInstance(requestList);

        //when
        ChangeApplyToApproveResponse response = applyInformationService.applyToApprove(missionId, request);

        //then
        assertEquals(request.getApproveTesterIdList().size(), response.getSuccessApplyInformationIdList().size());
        assertEquals(mockApplyInformationIdList[0], response.getSuccessApplyInformationIdList().get(0));

    }


    @Test
    @DisplayName("수행인원을 완료인원으로 변경 - 성공")
    public void performToCompleteSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        Mission mockMission = mock(Mission.class);
        when(missionRepository.findById(missionId)).thenReturn(Optional.ofNullable(mockMission));

        when(applyInformationRepository.existsApplyInformationByMissionIdAndExecutionTimeIsNull(missionId)).thenReturn(0L);
        Tester[] mockTesterList = new Tester[3];
        UUID[] mockTesterIdList = new UUID[3];
        UUID[] mockApplyInformationIdList = new UUID[3];

        List<ApplyInformation> mockApplyInformations = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
            mockApplyInformationIdList[i] = UUID.randomUUID();
            lenient().when(mockApplyInformation.getId()).thenReturn(mockApplyInformationIdList[i]);
            mockTesterList[i] = mock(Tester.class);
            mockTesterIdList[i] = UUID.randomUUID();
            when(mockApplyInformation.getTester()).thenReturn(mockTesterList[i]);
            when(mockTesterList[i].getId()).thenReturn(mockTesterIdList[i]);
            mockApplyInformations.add(mockApplyInformation);
        }
        when(mockMission.getApplyInformationList()).thenReturn(mockApplyInformations);

        List<UUID> requestList = new ArrayList<>();
        requestList.add(mockTesterList[0].getId());

        ChangePerformToCompleteRequest request = ChangePerformToCompleteRequest.newInstance(requestList);

        //when
        ChangePerformToCompleteResponse response = applyInformationService.performToComplete(missionId, request);

        //then
        assertEquals(request.getApproveTestIdList().size(), response.getCompleteTesterIdList().size());
        assertEquals(mockApplyInformationIdList[0], response.getCompleteTesterIdList().get(0));
    }

    @Test
    @DisplayName("신청정보를 기반으로 신청인원들을 조회")
    public void findApplyTesterListSuccessTest() throws Exception {
        //given

        // 조회를 위해 mocking
        UUID missionId = UUID.randomUUID();

        // 조회한 값을 받아오기 위한 mocking
        Mission mockMission = mock(Mission.class);
        when(missionRepository.findById(missionId)).thenReturn(Optional.ofNullable(mockMission));

        // 조회한 값에서, get 요청시 필요한 값을 위해 mocking
        List<ApplyInformation> applyInformationList = new ArrayList<>();
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
        applyInformationList.add(mockApplyInformation);
        when(mockMission.getApplyInformationList()).thenReturn(applyInformationList);
        Tester mockTester = mock(Tester.class);
        when(mockApplyInformation.getTester()).thenReturn(mockTester);

        // 기댓값이 잘 들어가는지 testerId로 기댓값 mocking
        UUID expectedId = UUID.randomUUID();
        when(mockTester.getId()).thenReturn(expectedId);

        // get 요청으로 인한 null point 방지 mocking
        when(mockApplyInformation.getStatus()).thenReturn(ApplyInformationStatus.APPLY);


        // Tester -> applyInformationList 조회를 위한 mocking
        List<ApplyInformation> applyInformationListFromTester = new ArrayList<>();
        ApplyInformation mockApplyInformationFromTester = mock(ApplyInformation.class);
        applyInformationListFromTester.add(mockApplyInformationFromTester);
        when(mockTester.getApplyInformationList()).thenReturn(applyInformationListFromTester);


        // 리뷰를 조회하기 위해 생성
        TesterReview mockTesterReview = mock(TesterReview.class);
        when(mockApplyInformationFromTester.getTesterReview()).thenReturn(mockTesterReview);

        // 리뷰의 response를 만들기 위해 필요한 mocking
        when(mockTesterReview.getApplyInformation()).thenReturn(mockApplyInformationFromTester);
        Mission mockMissionFromReview = mock(Mission.class);
        when(mockApplyInformationFromTester.getMission()).thenReturn(mockMissionFromReview);

        //when
        ApplyTesterListResponse response = applyInformationService.findApplyTesterList(missionId);

        //then
        assertEquals(expectedId, response.getApplyTesterDTOList().get(0).getId());
    }

    @Test
    @DisplayName("수행인원들을 조회")
    public void findPerformTesterListSuccessTest() throws Exception {
        //given

        // 조회를 위해 mocking
        UUID missionId = UUID.randomUUID();

        // 조회한 값을 받아오기 위한 mocking
        Mission mockMission = mock(Mission.class);
        when(missionRepository.findById(missionId)).thenReturn(Optional.ofNullable(mockMission));

        // 조회한 값에서, get 요청시 필요한 값을 위해 mocking
        List<ApplyInformation> applyInformationList = new ArrayList<>();
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
        applyInformationList.add(mockApplyInformation);
        when(mockMission.getApplyInformationList()).thenReturn(applyInformationList);
        Tester mockTester = mock(Tester.class);
        when(mockApplyInformation.getTester()).thenReturn(mockTester);

        // get 요청으로 인한 null point 방지 mocking
        when(mockApplyInformation.getStatus()).thenReturn(ApplyInformationStatus.APPLY);

        // 기댓값이 잘 들어가는지 testerId로 기댓값 mocking
        UUID expectedId = UUID.randomUUID();
        when(mockTester.getId()).thenReturn(expectedId);

        //when
        PerformTesterListResponse response = applyInformationService.findPerformTesterList(missionId);

        //then
        assertEquals(expectedId, response.getPerformTesterDTOList().get(0).getId());
    }

    @Test
    @DisplayName("종료된 Tester들을 조회 - 성공")
    public void findTesterListOfClosedMission() throws Exception {
        //given

        // 조회를 위해 mocking
        UUID missionId = UUID.randomUUID();

        // 조회한 값을 받아오기 위한 mocking
        Mission mockMission = mock(Mission.class);
        when(missionRepository.findById(missionId)).thenReturn(Optional.ofNullable(mockMission));

        // 조회한 값에서, get 요청시 필요한 값을 위해 mocking
        List<ApplyInformation> applyInformationList = new ArrayList<>();
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
        applyInformationList.add(mockApplyInformation);
        when(mockMission.getApplyInformationList()).thenReturn(applyInformationList);
        Tester mockTester = mock(Tester.class);
        when(mockApplyInformation.getTester()).thenReturn(mockTester);

        // get 요청으로 인한 null point 방지 mocking
        when(mockApplyInformation.getStatus()).thenReturn(ApplyInformationStatus.APPLY);

        // 기댓값이 잘 들어가는지 testerId로 기댓값 mocking
        UUID expectedId = UUID.randomUUID();
        when(mockApplyInformation.getId()).thenReturn(expectedId);

        //when
        TesterListOfClosedMissionResponse response = applyInformationService.findTesterListOfClosedMission(missionId);

        //then
        assertEquals(expectedId, response.getTesterListOfClosedTestList().get(0).getId());
    }

    @Test
    @DisplayName("api상 필요한 count 조회 테스트 - 성공")
    public void findCountSuccessTest() throws Exception {
        //given
        long expectedTesterCount = 10;
        long expectedMakerCount = 20;
        long progressMissionCount = 5;
        long completeMissionCount = 4;
        when(testerRepository.count()).thenReturn(expectedTesterCount);
        when(makerRepository.count()).thenReturn(expectedMakerCount);
        LocalDate currentDate = LocalDate.now();
        when(missionRepository.findProgressMission(currentDate)).thenReturn(progressMissionCount);
        when(missionRepository.findCompleteMission(currentDate)).thenReturn(completeMissionCount);

        //when
        CountResponse response = applyInformationService.findCount();

        //then
        assertEquals(expectedTesterCount,response.getTesterCount());
        assertEquals(expectedMakerCount,response.getMakerCount());
        assertEquals(progressMissionCount,response.getContinueTestCount());
        assertEquals(completeMissionCount,response.getCompleteTestCount());

    }



}