package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.*;
import io.wisoft.testermatchingplatform.handler.FileHandler;
import io.wisoft.testermatchingplatform.handler.exception.service.MakerNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.service.MissionNotFoundException;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.repository.MissionRepository;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMissionRequest;
import io.wisoft.testermatchingplatform.web.dto.request.UpdateMissionExceptImageRequest;
import io.wisoft.testermatchingplatform.web.dto.request.UpdateMissionIncludeImageRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final MakerRepository makerRepository;
    private final ApplyInformationRepository applyInformationRepository;

    @Transactional
    public CreateMissionResponse createMission(UUID makerId, CreateMissionRequest request) {
        Maker maker = makerRepository.findById(makerId).orElseThrow(
                () -> new MakerNotFoundException("id: " + makerId + " not found")
        );
        String imageFileURL = FileHandler.saveTestImageFileData(request.getImage());
        Mission mission = request.toTest(maker, imageFileURL);

        Mission storedMission = missionRepository.save(mission);

        CreateMissionResponse response = CreateMissionResponse.fromMission(storedMission);

        return response;
    }

    @Transactional
    public UpdateMissionIncludeImageResponse updateIncludeImageMission(UUID missionId, UpdateMissionIncludeImageRequest request) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );
        FileHandler.removeTestImage(mission.getImageURL());
        String imageFileURL = FileHandler.saveTestImageFileData(request.getImage());

        mission.updateIncludeImageMission(
                request.getTitle(),
                request.getContent(),
                imageFileURL,
                request.getReward(),
                request.getLimitPerformer(),
                LocalDate.parse(request.getRecruitmentTimeStart(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getRecruitmentTimeLimit(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getDurationTimeStart(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getDurationTimeLimit(), DateTimeFormatter.ISO_LOCAL_DATE)
        );

        UpdateMissionIncludeImageResponse response = UpdateMissionIncludeImageResponse.fromMission(mission);
        return response;
    }

    @Transactional
    public UpdateMissionExceptImageResponse updateExceptImageMission(UUID missionId, UpdateMissionExceptImageRequest request) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );

        mission.updateExceptImageMission(
                request.getTitle(),
                request.getContent(),
                request.getReward(),
                request.getLimitPerformer(),
                LocalDate.parse(request.getRecruitmentTimeStart(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getRecruitmentTimeLimit(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getDurationTimeStart(), DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(request.getDurationTimeLimit(), DateTimeFormatter.ISO_LOCAL_DATE)
        );

        UpdateMissionExceptImageResponse response = UpdateMissionExceptImageResponse.fromMission(mission);
        return response;
    }

    public DetailMissionResponse detailMission(UUID missionId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );

        DetailMissionResponse response = DetailMissionResponse.fromMission(mission);
        return response;
    }


    public SimpleMissionListResponse applyMissionList() {
        LocalDate currentDate = LocalDate.now();
        List<Mission> missionList = missionRepository.findApplyMissions(currentDate);
        SimpleMissionListResponse response = SimpleMissionListResponse.fromMissionList(missionList);
        return response;
    }

    public ApplyMissionListFromTesterResponse applyMissionListFromTester(UUID testerId) {
        List<ApplyInformation> applyInformationList = applyInformationRepository.findByTesterId(testerId);

        ApplyMissionListFromTesterResponse response = ApplyMissionListFromTesterResponse.fromApplyInformationList(
                applyInformationList
        );

        return response;
    }

    public SimpleMissionListResponse applyMissionListByCreated(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        List<Mission> testList = missionRepository.findApplyMissionsExceptTesterIdByCreated(testerId, currentDate);
        SimpleMissionListResponse response = SimpleMissionListResponse.fromMissionList(testList);

        return response;
    }

    public SimpleMissionListResponse applyMissionListByDeadLine(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        List<Mission> missionList = missionRepository.findApplyMissionsExceptTesterIdByDeadLine(testerId, currentDate);
        SimpleMissionListResponse response = SimpleMissionListResponse.fromMissionList(missionList);

        return response;
    }

    public SimpleMissionListResponse applyMissionListByPopular(UUID testerId) {
        LocalDate currentDate = LocalDate.now();
        List<Mission> missionList = missionRepository.findApplyMissionsExceptTesterIdByPopular(testerId, currentDate);
        SimpleMissionListResponse response = SimpleMissionListResponse.fromMissionList(missionList);

        return response;
    }

    public ApplyMissionListFromMakerResponse madeMissionListFromMaker(UUID makerId) {
        List<Mission> missionList = missionRepository.findAppliedMissionsByMakerId(makerId);
        ApplyMissionListFromMakerResponse response = ApplyMissionListFromMakerResponse.fromMissionList(missionList);

        return response;
    }

    public Top4MissionListResponse top4Popular() {
        LocalDate currentDate = LocalDate.now();
        List<Mission> popularTop4MissionList = missionRepository.findPopularTop4Mission(currentDate);

        Top4MissionListResponse response = Top4MissionListResponse.toMissionList(popularTop4MissionList);

        return response;
    }

    public Top4MissionListResponse top4Deadline() {
        LocalDate currentDate = LocalDate.now();
        List<Mission> deadLineTop4MissionList = missionRepository.findDeadlineTop4Mission(currentDate);

        Top4MissionListResponse response = Top4MissionListResponse.toMissionList(deadLineTop4MissionList);

        return response;
    }




}
