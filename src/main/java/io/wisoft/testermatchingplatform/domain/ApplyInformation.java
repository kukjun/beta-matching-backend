package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.MissionStatusMismatchException;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class ApplyInformation extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
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


    @OneToOne(mappedBy = "applyInformation", fetch = FetchType.LAZY)
    private TesterReview testerReview;

    @OneToOne(mappedBy = "applyInformation", fetch = FetchType.LAZY)
    private MakerReview makerReview;

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

    public void disconnect() {
        disconnectTester();
        disconnectMission();
    }

    public void connectTesterReview(TesterReview testerReview) {
        this.testerReview = testerReview;
    }

    public void connectMakerReview(MakerReview makerReview) {
        this.makerReview = makerReview;
    }

    private void disconnectMission() {
        mission.getApplyInformationList().remove(this);
    }

    private void disconnectTester() {
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
        isMissionStatsMatch(MissionStatus.APPROVE);
        this.approveTime = LocalDateTime.now();
        this.status = ApplyInformationStatus.APPROVE_SUCCESS;
        updateEntity();
    }


    public void applyReject() {
        isMissionStatsMatch(MissionStatus.APPROVE);
        this.approveTime = LocalDateTime.now();
        this.status = ApplyInformationStatus.APPROVE_FAIL;
        mission.getMaker().refundPoint(mission.getReward());
        updateEntity();
    }


    public void executeApprove() {
        isMissionStatsMatch(MissionStatus.PROGRESS);
        this.executionTime = LocalDateTime.now();
        this.status = ApplyInformationStatus.EXECUTE_SUCCESS;
        tester.rewardPoint(mission.getReward());
        updateEntity();
    }

    public void executeReject() {
        isMissionStatsMatch(MissionStatus.PROGRESS);
        this.executionTime = LocalDateTime.now();
        this.status = ApplyInformationStatus.EXECUTE_FAIL;
        mission.getMaker().refundPoint(mission.getReward());
        updateEntity();
    }

    public MissionStatus currentMissionStatus() {
        return this.mission.getStatus();
    }

    /**
     * 예외 처리 메서드
     */
    public void isMissionStatsMatch(MissionStatus expectedMissionStatus) {
        MissionStatus actualMissionStatus = currentMissionStatus();
        if (expectedMissionStatus != actualMissionStatus) {
            throw new MissionStatusMismatchException(
                    "expected: " + expectedMissionStatus.toString() +
                            "\n actual: " + actualMissionStatus.toString()
            );
        }
    }
}
