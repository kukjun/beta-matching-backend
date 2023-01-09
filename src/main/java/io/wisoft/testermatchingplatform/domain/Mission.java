package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.NotNaturalNumberException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Test 가 TestFrameWork와 겹쳐서, Tests로 변경
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "mission_id")
    private UUID id;

    private String title;
    @Column(columnDefinition = "text")
    private String content;
    private String imageURL;
    private long reward;
    private int limitPerformer;

    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    @Embedded
    private MissionDate missionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maker_id")
    private Maker maker;

    @OneToMany(mappedBy = "mission")
    private List<ApplyInformation> applyInformationList = new ArrayList<>();


    /**
     * 연관관계 편의 메서드
     */
    public void connectCreatedMaker(Maker maker) {
        this.maker = maker;
        maker.getCreatedMissions().add(this);
    }

    public void refreshMissionStatus() {
        this.status = MissionStatus.refreshStatus(this.missionDate);
    }

    public void disconnectCreateMaker(Maker maker) {
        maker.getCreatedMissions().remove(this);
    }

    /**
     * 정적 생성자 메서드
     */

    // 생성 시 예외. recruitmentTimeStart가 현재보다 적으면 Error
    public static Mission newInstance(
            final String title,
            final String content,
            final String imageURL,
            final long point,
            final int limitPerformer,
            final Maker maker,
            final LocalDate recruitmentTimeStart,
            final LocalDate recruitmentTimeEnd,
            final LocalDate durationTimeStart,
            final LocalDate durationTimeEnd
    ) {
        Mission mission = new Mission();
        mission.isLimitPerformerNaturalNumber(limitPerformer);
        mission.title = title;
        mission.content = content;
        mission.imageURL = imageURL;
        mission.reward = point;
        mission.missionDate = MissionDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
        mission.limitPerformer = limitPerformer;
        mission.maker = maker;
        maker.usePoint(point * limitPerformer);
        mission.refreshMissionStatus();
        mission.createEntity();
        return mission;
    }

    /**
     * 비지니스 메서드
     */

    public void updateIncludeImageMission(
            final String title,
            final String content,
            final String imageURL,
            final long reward,
            final int limitPerformer,
            final LocalDate recruitmentTimeStart,
            final LocalDate recruitmentTimeEnd,
            final LocalDate durationTimeStart,
            final LocalDate durationTimeEnd
    ) {
        isLimitPerformerNaturalNumber(limitPerformer);
        this.maker.updatePoint(reward * limitPerformer - this.reward * this.limitPerformer);
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
        this.reward = reward;
        this.missionDate = MissionDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
        this.limitPerformer = limitPerformer;
        updateEntity();
    }

    public void updateExceptImageMission(
            final String title,
            final String content,
            final long reward,
            final int limitPerformer,
            final LocalDate recruitmentTimeStart,
            final LocalDate recruitmentTimeEnd,
            final LocalDate durationTimeStart,
            final LocalDate durationTimeEnd
    ) {
        isLimitPerformerNaturalNumber(limitPerformer);
        this.maker.updatePoint(reward * limitPerformer - this.reward * this.limitPerformer);
        this.title = title;
        this.content = content;
        this.reward = reward;
        this.missionDate = MissionDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
        this.limitPerformer = limitPerformer;
        updateEntity();
    }

    public long remainApplyDays() {
        long remainTime = missionDate.remainApplyTime();
        return remainTime;
    }

    /**
     * 예외 처리 로직
     */
    private void isLimitPerformerNaturalNumber(int limitPerformer) {
        if (limitPerformer <= 0) {
            throw new NotNaturalNumberException(String.valueOf(limitPerformer));
        }
    }

}
