package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.handler.exception.service.ApplyInformationNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.service.MissionNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.service.TesterNotFoundException;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.repository.MissionRepository;
import io.wisoft.testermatchingplatform.repository.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.request.ChangeApplyToApproveRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePerformToCompleteRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyInformationService {

    private final ApplyInformationRepository applyInformationRepository;
    private final MissionRepository missionRepository;
    private final TesterRepository testerRepository;

    private final MakerRepository makerRepository;


    @Transactional
    public ApplyMissionResponse applyMission(UUID testerId, UUID missionId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );
        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("id: " + testerId + " not found")
        );
        ApplyInformation applyInformation = ApplyInformation.newInstance(
                mission, tester
        );

        ApplyInformation storedApplyInformation = applyInformationRepository.save(applyInformation);

        return ApplyMissionResponse.fromApplyInformation(storedApplyInformation);
    }

    @Transactional
    public void cancelApply(UUID applyInformationId) {
        ApplyInformation applyInformation = applyInformationRepository.findById(applyInformationId).orElseThrow(
                () -> new ApplyInformationNotFoundException("id: " + applyInformationId + " not found")
        );

        applyInformation.disconnect();
        applyInformationRepository.deleteById(applyInformationId);
    }

    @Transactional
    public void cancelApply(UUID testerId, UUID missionId) {
        ApplyInformation applyInformation = applyInformationRepository.findApplyInformationByTesterIdAndMissionId(testerId, missionId).orElseThrow(
                () -> new ApplyInformationNotFoundException("tester id: " + testerId + ", mission id: " + missionId + " not found")
        );

        applyInformation.disconnect();
        applyInformationRepository.deleteById(applyInformation.getId());
    }

    @Transactional
    public ChangeApplyToApproveResponse applyToApprove(UUID missionId, ChangeApplyToApproveRequest request) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );
        List<ApplyInformation> applyInformationList = mission.getApplyInformationList();
        List<ApplyInformation> responseList = new ArrayList<>();

        HashSet<UUID> set = new HashSet<>(request.getApproveTesterIdList());
        for (ApplyInformation applyInformation : applyInformationList) {
            if (set.contains(applyInformation.getId())) {
                applyInformation.applyApprove();
                responseList.add(applyInformation);
            } else {
                applyInformation.applyReject();
            }
        }

        ChangeApplyToApproveResponse response = ChangeApplyToApproveResponse.fromApplyInformationList(responseList);
        return response;
    }

    @Transactional
    public ChangePerformToCompleteResponse performToComplete(UUID missionId, ChangePerformToCompleteRequest request) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );
        List<ApplyInformation> applyInformationList = mission.getApplyInformationList();
        List<ApplyInformation> responseList = new ArrayList<>();

        HashSet<UUID> set = new HashSet<>(request.getApproveTestIdList());
        for (ApplyInformation applyInformation : applyInformationList) {
            if (set.contains(applyInformation.getId())) {
                applyInformation.executeApprove();
                responseList.add(applyInformation);
            } else {
                applyInformation.executeReject();
            }
        }

        ChangePerformToCompleteResponse response = ChangePerformToCompleteResponse.fromApplyInformationList(responseList);
        return response;
    }

    public ApplyTesterListResponse findApplyTesterList(UUID missionId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );
        List<ApplyInformation> applyInformationList = mission.getApplyInformationList();

        ApplyTesterListResponse response = ApplyTesterListResponse.fromApplyInformationList(applyInformationList);
        return response;
    }

    public PerformTesterListResponse findPerformTesterList(UUID missionId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );
        List<ApplyInformation> applyInformationList = mission.getApplyInformationList();

        PerformTesterListResponse response = PerformTesterListResponse.fromApplyInformationList(applyInformationList);
        return response;
    }

    public TesterListOfClosedMissionResponse findTesterListOfClosedMission(UUID missionId) {
        Mission mission = missionRepository.findById(missionId).orElseThrow(
                () -> new MissionNotFoundException("id: " + missionId + " not found")
        );
        List<ApplyInformation> applyInformationList = mission.getApplyInformationList();

        TesterListOfClosedMissionResponse response = TesterListOfClosedMissionResponse.fromApplyInformationList(applyInformationList);
        return response;
    }

    public CountResponse findCount() {
        long testerCount = testerRepository.count();
        long makerCount = makerRepository.count();
        LocalDate currentDate = LocalDate.now();
        long continueMissionCount = missionRepository.findProgressMission(currentDate);
        long completeMissionCount = missionRepository.findCompleteMission(currentDate);

        CountResponse response = CountResponse.newInstance(
                testerCount,
                makerCount,
                continueMissionCount,
                completeMissionCount
        );

        return response;
    }


    public UUID findApplyInformationId(UUID testerId, UUID missionId) {
        ApplyInformation applyInformation = applyInformationRepository.findApplyInformationByTesterIdAndMissionId(testerId, missionId).orElseThrow(
                () -> new ApplyInformationNotFoundException("tester id: " + testerId + ", mission id: " + missionId + " not found")
        );
        return applyInformation.getId();

    }
}
