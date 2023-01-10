package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.*;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.repository.MissionRepository;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMissionRequest;
import io.wisoft.testermatchingplatform.web.dto.request.UpdateMissionExceptImageRequest;
import io.wisoft.testermatchingplatform.web.dto.request.UpdateMissionIncludeImageRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {

    @Mock
    private MakerRepository makerRepository;
    @Mock
    private MissionRepository missionRepository;
    @Mock
    private ApplyInformationRepository applyInformationRepository;

    private MissionService missionService;

    @BeforeEach
    public void prepareTest() {
        missionService = new MissionService(missionRepository, makerRepository, applyInformationRepository);
    }

    @Test
    @DisplayName("Mission 생성 테스트 - 성공")
    public void createMissionSuccessTest() throws Exception {
        //given
        MultipartFile multipartFile = createMultipartFileForTest();
        String expectURL = "testURL";

        CreateMissionRequest request = CreateMissionRequest.newInstance(
                "Mission Create",
                LocalDate.now().toString(),
                LocalDate.now().plusDays(5).toString(),
                LocalDate.now().plusDays(10).toString(),
                LocalDate.now().plusDays(20).toString(),
                "Mission Content",
                100L,
                20,
                multipartFile
        );

        UUID makerId = UUID.randomUUID();
        Maker mockMaker = mock(Maker.class);
        when(makerRepository.findById(makerId)).thenReturn(Optional.ofNullable(mockMaker));
        UUID missionId = UUID.randomUUID();
        Mission mockMission = mock(Mission.class);
        when(mockMission.getId()).thenReturn(missionId);
        when(missionRepository.save(any(Mission.class))).thenReturn(mockMission);

        //when
        try (MockedStatic<FileHandler> fileHandlerMockedStatic = mockStatic(FileHandler.class)) {
            fileHandlerMockedStatic.when(() -> FileHandler.saveTestImageFileData(request.getImage()))
                    .thenReturn(expectURL);

            CreateMissionResponse response = missionService.createMission(makerId, request);
            //then
            assertEquals(missionId, response.getId());

        }
    }

    @Test
    @DisplayName("Image를 포함한 Mission 생성 테스트 - 성공")
    public void updateMissionIncludeImageSuccessTest() throws Exception {
        //given
        MultipartFile multipartFile = createMultipartFileForTest();
        String expectURL = "anotherURL";

        UpdateMissionIncludeImageRequest request = UpdateMissionIncludeImageRequest.newInstance(
                "Mission Update",
                LocalDate.now().toString(),
                LocalDate.now().plusDays(5).toString(),
                LocalDate.now().plusDays(10).toString(),
                LocalDate.now().plusDays(20).toString(),
                "Mission Content",
                100L,
                20,
                multipartFile
        );

        UUID missionId = UUID.randomUUID();
        Mission mockMission = mock(Mission.class);
        when(mockMission.getId()).thenReturn(missionId);

        when(missionRepository.findById(missionId)).thenReturn(Optional.of(mockMission));

        //when
        try (MockedStatic<FileHandler> fileHandlerMockedStatic = mockStatic(FileHandler.class)) {
            fileHandlerMockedStatic.when(() -> FileHandler.saveTestImageFileData(request.getImage()))
                    .thenReturn(expectURL);

            UpdateMissionIncludeImageResponse response = missionService.updateIncludeImageMission(missionId, request);

            //then
            assertEquals(missionId, response.getId());
        }
    }

    @Test
    @DisplayName("Image를 제외한 Mission 생성 테스트 - 성공")
    public void updateMissionExceptImageSuccessTest() throws Exception {
        //given
        UpdateMissionExceptImageRequest request = UpdateMissionExceptImageRequest.newInstance(
                "Mission Update",
                LocalDate.now().toString(),
                LocalDate.now().plusDays(5).toString(),
                LocalDate.now().plusDays(10).toString(),
                LocalDate.now().plusDays(20).toString(),
                "Mission Content",
                100L,
                20
        );

        UUID missionId = UUID.randomUUID();
        Mission mockMission = mock(Mission.class);
        when(mockMission.getId()).thenReturn(missionId);
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(mockMission));
        //when
        UpdateMissionExceptImageResponse response = missionService.updateExceptImageMission(missionId, request);

        //then
        assertEquals(missionId, response.getId());
    }

    @Test
    @DisplayName("신청기간의 Mission List 조회 - 성공")
    public void applyMissionListSuccessTest() throws Exception {
        //given
        Mission mockMission = mock(Mission.class);
        UUID expectMissionId = UUID.randomUUID();
        when(mockMission.getId()).thenReturn(expectMissionId);
        Maker mockMaker = mock(Maker.class);
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);

        List<Mission> missions = new ArrayList<>();
        missions.add(mockMission);

        List<ApplyInformation> applyInformations = new ArrayList<>();
        applyInformations.add(mockApplyInformation);

        when(mockMission.getMaker()).thenReturn(mockMaker);
        when(mockMission.getApplyInformationList()).thenReturn(applyInformations);

        LocalDate currentDate = LocalDate.now();
        when(missionRepository.findApplyMissions(currentDate))
                .thenReturn(missions);

        //when
        SimpleMissionListResponse response = missionService.applyMissionList();

        //then
        assertEquals(expectMissionId, response.getDtoList().get(0).getId());
    }

    @Test
    @DisplayName("테스터가 신청한 신청기간의 Mission List 가져오기")
    public void applyMissionListFromTesterSuccessTest() throws Exception {
        //given
        List<ApplyInformation> applyInformationList = new ArrayList<>();
        // 3개의 리스트이므로 3개 리스트로 분리
        ApplyInformation[] mockApplyInformations = new ApplyInformation[3];
        Mission[] mockMissions = new Mission[3];
        Maker[] mockMakers = new Maker[3];
        UUID[] expectedIds = new UUID[3];

        for (int i = 0; i < 3; i++) {
            mockApplyInformations[i] = mock(ApplyInformation.class);
            mockMissions[i] = mock(Mission.class);
            mockMakers[i] = mock(Maker.class);
            expectedIds[i] = UUID.randomUUID();
            when(mockMissions[i].getId()).thenReturn(expectedIds[i]);

            List<ApplyInformation> innerApplyInformation = new ArrayList<>();
            innerApplyInformation.add(mockApplyInformations[i]);


            when(mockMissions[i].getMaker()).thenReturn(mockMakers[i]);
            when(mockApplyInformations[i].getMission()).thenReturn(mockMissions[i]);

            switch (i) {
                case 0:
                    when(mockApplyInformations[i].getStatus()).thenReturn(ApplyInformationStatus.APPLY);
                    when(mockMissions[i].getApplyInformationList()).thenReturn(innerApplyInformation);
                    break;
                case 1:
                    when(mockApplyInformations[i].getStatus()).thenReturn(ApplyInformationStatus.APPROVE_SUCCESS);
                    break;
                case 2:
                    when(mockApplyInformations[i].getStatus()).thenReturn(ApplyInformationStatus.EXECUTE_SUCCESS);
                    break;
            }

            applyInformationList.add(mockApplyInformations[i]);
        }

        UUID testerId = UUID.randomUUID();
        when(applyInformationRepository.findByTesterId(testerId)).thenReturn(applyInformationList);

        //when
        ApplyMissionListFromTesterResponse response = missionService.applyMissionListFromTester(testerId);

        //then
        assertEquals(expectedIds[0], response.getAppliedMissionDTOList().get(0).getId());
        assertEquals(expectedIds[1], response.getApprovedMissionDTOList().get(0).getId());
        assertEquals(expectedIds[2], response.getExecutedMissionDTOList().get(0).getId());
    }

    @Test
    @DisplayName("생성일 기준으로 정렬한 MissionList 가져오기")
    public void applyMissionListByCreatedSuccessTest() throws Exception {
        //given
        Mission mockMission = mock(Mission.class);
        UUID expectMissionId = UUID.randomUUID();
        when(mockMission.getId()).thenReturn(expectMissionId);
        Maker mockMaker = mock(Maker.class);
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);

        List<Mission> missions = new ArrayList<>();
        missions.add(mockMission);

        List<ApplyInformation> applyInformations = new ArrayList<>();
        applyInformations.add(mockApplyInformation);

        when(mockMission.getMaker()).thenReturn(mockMaker);
        when(mockMission.getApplyInformationList()).thenReturn(applyInformations);

        UUID testerId = UUID.randomUUID();
        LocalDate currentDate = LocalDate.now();
        when(missionRepository.findApplyMissionsExceptTesterIdByCreated(testerId, currentDate))
                .thenReturn(missions);

        //when
        SimpleMissionListResponse response = missionService.applyMissionListByCreated(testerId);

        //then
        assertEquals(expectMissionId, response.getDtoList().get(0).getId());
    }

    @Test
    @DisplayName("마감기간 기준으로 정렬한 MissionList 가져오기")
    public void applyMissionListByDeadlineSuccessTest() throws Exception {
        //given
        Mission mockMission = mock(Mission.class);
        UUID expectMissionId = UUID.randomUUID();
        when(mockMission.getId()).thenReturn(expectMissionId);
        Maker mockMaker = mock(Maker.class);
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);

        List<Mission> missions = new ArrayList<>();
        missions.add(mockMission);

        List<ApplyInformation> applyInformations = new ArrayList<>();
        applyInformations.add(mockApplyInformation);

        when(mockMission.getMaker()).thenReturn(mockMaker);
        when(mockMission.getApplyInformationList()).thenReturn(applyInformations);

        UUID testerId = UUID.randomUUID();
        LocalDate currentDate = LocalDate.now();
        when(missionRepository.findApplyMissionsExceptTesterIdByDeadLine(testerId, currentDate))
                .thenReturn(missions);

        //when
        SimpleMissionListResponse response = missionService.applyMissionListByDeadLine(testerId);

        //then
        assertEquals(expectMissionId, response.getDtoList().get(0).getId());
    }

    @Test
    @DisplayName("인기 기준으로 정렬한 MissionList 가져오기")
    public void applyMissionListByPopularSuccessTest() throws Exception {
        //given
        Mission mockMission = mock(Mission.class);
        UUID expectMissionId = UUID.randomUUID();
        when(mockMission.getId()).thenReturn(expectMissionId);
        Maker mockMaker = mock(Maker.class);
        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);

        List<Mission> missions = new ArrayList<>();
        missions.add(mockMission);

        List<ApplyInformation> applyInformations = new ArrayList<>();
        applyInformations.add(mockApplyInformation);

        when(mockMission.getMaker()).thenReturn(mockMaker);
        when(mockMission.getApplyInformationList()).thenReturn(applyInformations);

        UUID testerId = UUID.randomUUID();
        LocalDate currentDate = LocalDate.now();
        when(missionRepository.findApplyMissionsExceptTesterIdByPopular(testerId, currentDate))
                .thenReturn(missions);

        //when
        SimpleMissionListResponse response = missionService.applyMissionListByPopular(testerId);

        //then
        assertEquals(expectMissionId, response.getDtoList().get(0).getId());
    }

    @Test
    @DisplayName("Maker가 생성한 MissionList Test")
    public void madeMissionListFromMakerSuccessTest() throws Exception {
        //given
        List<Mission> missionList = new ArrayList<>();
        // 4개의 리스트이므로 4개 리스트로 분리
        Mission[] mockMissions = new Mission[4];
        ApplyInformation[] mockApplyInformations = new ApplyInformation[4];
        Maker[] mockMakers = new Maker[4];
        UUID[] expectedIds = new UUID[4];

        for (int i = 0; i < 4; i++) {
            mockMissions[i] = mock(Mission.class);
            mockMakers[i] = mock(Maker.class);
            expectedIds[i] = UUID.randomUUID();
            when(mockMissions[i].getId()).thenReturn(expectedIds[i]);
            when(mockMissions[i].getMaker()).thenReturn(mockMakers[i]);

            switch (i) {
                case 0:
                    when(mockMissions[i].getStatus()).thenReturn(MissionStatus.APPLY);
                    mockApplyInformations[i] = mock(ApplyInformation.class);
                    List<ApplyInformation> innerApplyInformation = new ArrayList<>();
                    innerApplyInformation.add(mockApplyInformations[i]);
                    when(mockMissions[i].getApplyInformationList()).thenReturn(innerApplyInformation);
                    break;
                case 1:
                    when(mockMissions[i].getStatus()).thenReturn(MissionStatus.APPROVE);
                    mockApplyInformations[i] = mock(ApplyInformation.class);
                    List<ApplyInformation> approveApplyInformation = new ArrayList<>();
                    approveApplyInformation.add(mockApplyInformations[i]);
                    when(mockMissions[i].getApplyInformationList()).thenReturn(approveApplyInformation);
                    break;
                case 2:
                    when(mockMissions[i].getStatus()).thenReturn(MissionStatus.PROGRESS);
                    break;
                case 3:
                    when(mockMissions[i].getStatus()).thenReturn(MissionStatus.COMPLETE);
                    break;
            }

            missionList.add(mockMissions[i]);
        }
        UUID makerId = UUID.randomUUID();
        when(missionRepository.findAppliedMissionsByMakerId(makerId)).thenReturn(missionList);
        //when
        ApplyMissionListFromMakerResponse response = missionService.madeMissionListFromMaker(makerId);

        //then
        assertEquals(expectedIds[0], response.getApplyPeriodTestDTOList().get(0).getId());
        assertEquals(expectedIds[1], response.getApprovePeriodTestDTOList().get(0).getId());
        assertEquals(expectedIds[2], response.getProgressPeriodTestDTOList().get(0).getId());
        assertEquals(expectedIds[3], response.getCompletePeriodTestDTOList().get(0).getId());
    }


    @Test
    @DisplayName("mission 주요 정보 조회 테스트 - 성공")
    public void detailMissionSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        Mission mockMission = mock(Mission.class);
        when(mockMission.getId()).thenReturn(missionId);
        when(mockMission.getMaker()).thenReturn(mock(Maker.class));
        when(mockMission.getMissionDate()).thenReturn(mock(MissionDate.class));
        when(mockMission.getApplyInformationList()).thenReturn(new ArrayList<>());
        when(missionRepository.findById(missionId)).thenReturn(Optional.of(mockMission));

        //when
        DetailMissionResponse response = missionService.detailMission(missionId);

        //then
        assertEquals(missionId, response.getId());
    }





    private MultipartFile createMultipartFileForTest() throws IOException {
        String testFilePath = System.getProperty("user.dir") + "/src/test/resources/";
        String fieldName = "CreateMissionImage.png";
        File file = new File(testFilePath + fieldName);
        FileItem fileItem = new DiskFileItem(fieldName, Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
        } catch (IOException ex) {
        }

        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        return multipartFile;
    }


}