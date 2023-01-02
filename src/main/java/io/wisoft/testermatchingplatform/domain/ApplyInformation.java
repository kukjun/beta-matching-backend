package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ApproveException;
import io.wisoft.testermatchingplatform.handler.exception.ExecutionException;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class ApplyInformation extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "apply_information_id")
    private UUID id;

    private LocalDateTime registerTime;
    private LocalDateTime approveTime;
    private LocalDateTime executionTime;

    @Enumerated(EnumType.STRING)
    private ApplyInformationStatus status;

    @JoinColumn(name = "mission_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mission mission;

    @JoinColumn(name = "tester_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Tester tester;

    /**
     * 연관관계 설정 메서드
     */
    public void connectMission(Mission mission) {
        this.mission = mission;
        mission.getApplyInformationList().add(this);
    }

    public void connectTester(Tester tester) {
        this.tester = tester;
        tester.getApplyInformationList().add(this);
    }

    public void disconnectLMission() {
        mission.getApplyInformationList().remove(this);
    }

    public void disconnectTester() {
        tester.getApplyInformationList().remove(this);
    }

    /**
     * 정적 생성자 메서드
     */
    public static ApplyInformation newInstance(
            Mission mission,
            Tester tester
    ) {
        ApplyInformation applyInformation = new ApplyInformation();
        applyInformation.connectMission(mission);
        applyInformation.connectTester(tester);
        applyInformation.registerTime = LocalDateTime.now();
        applyInformation.status = ApplyInformationStatus.APPLY;
        applyInformation.createEntity();
        return applyInformation;
    }

    /**
     * 비지니스 메서드
     */
    public void applyApprove() {
        MissionStatus missionStatus = MissionStatus.refreshStatus(currentTestDate());
        if (missionStatus == MissionStatus.APPROVE) {
            this.approveTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.APPROVE_SUCCESS;
        } else {
            throw new ApproveException("선정 기간이 아닙니다.");
        }
    }

    public void applyReject() {
        MissionStatus missionStatus = MissionStatus.refreshStatus(currentTestDate());
        if (missionStatus == MissionStatus.APPROVE) {
            this.approveTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.APPROVE_FAIL;
            mission.getMaker().refundPoint(mission.getReward());
        } else {
            throw new ApproveException("선정기간이 아닙니다.");
        }
    }


    public void executionApprove() {
        MissionStatus missionStatus = MissionStatus.refreshStatus(this.mission.getMissionDate());
        if (missionStatus == MissionStatus.PROGRESS) {
            this.executionTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.EXECUTE_SUCCESS;
            tester.rewardPoint(mission.getReward());
        } else {
            throw new ExecutionException("수행 기간이 아닙니다.");
        }
    }

    public void executeReject() {
        MissionStatus missionStatus = MissionStatus.refreshStatus(currentTestDate());
        if (missionStatus == MissionStatus.PROGRESS) {
            this.executionTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.EXECUTE_FAIL;
            mission.getMaker().refundPoint(mission.getReward());
        } else {
            throw new ExecutionException("수행 기간, 완료 기간이 아닙니다.");
        }
    }

    private MissionDate currentTestDate() {
        return this.mission.getMissionDate();
    }

    public MissionStatus currentTestStatus() {
        return this.mission.getStatus();
    }


}
