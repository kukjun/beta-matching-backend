package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MissionRepository;
import io.wisoft.testermatchingplatform.repository.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.request.ChangeApplyToApproveRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePerformToCompleteRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyInformationService {

    private final ApplyInformationRepository applyInformationRepository;
    private final MissionRepository missionRepository;
    private final TesterRepository testerRepository;


    @Transactional
    public ApplyMissionResponse applyMission(UUID testerId, UUID missionId) {
        Mission mission = missionRepository.findById(missionId);
        Tester tester = testerRepository.findById(testerId);
        ApplyInformation applyInformation = ApplyInformation.newInstance(
                mission, tester
        );

        UUID applyId = applyInformationRepository.save(applyInformation);

        return ApplyMissionResponse.newInstance(applyId);
    }

    @Transactional
    public void cancelApply(UUID applyInformationId) {
        ApplyInformation applyInformation = applyInformationRepository.findById(applyInformationId);
        applyInformation.disconnect();
        applyInformationRepository.deleteById(applyInformationId);
    }

    @Transactional
    public ChangeApplyToApproveResponse applyToApprove(UUID MissionId, ChangeApplyToApproveRequest request) {
        Mission mission = missionRepository.findById(MissionId);
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
        Mission mission = missionRepository.findById(missionId);
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
        Mission mission = missionRepository.findById(missionId);
        List<ApplyInformation> applyInformationList = mission.getApplyInformationList();

        ApplyTesterListResponse response = ApplyTesterListResponse.fromApplyInformationList(applyInformationList);
        return response;
    }

    public PerformTesterListResponse findPerformTesterList(UUID missionId) {
        Mission mission = missionRepository.findById(missionId);
        List<ApplyInformation> applyInformationList = mission.getApplyInformationList();

        PerformTesterListResponse response = PerformTesterListResponse.fromApplyInformationList(applyInformationList);
        return response;
    }

    public TesterListOfClosedMissionResponse TesterListOfClosedMission(UUID missionId) {
        Mission mission = missionRepository.findById(missionId);
        List<ApplyInformation> applyInformationList = mission.getApplyInformationList();

        TesterListOfClosedMissionResponse response = TesterListOfClosedMissionResponse.fromApplyInformationList(applyInformationList);
        return response;
    }


}
