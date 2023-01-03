package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ReviewException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterReview extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "tester_review_id")
    private UUID id;

    private LocalDateTime registerTime;
    private int starPoint;
    private String comment;

    @JoinColumn(name = "apply_information_id")
    @OneToOne(fetch = FetchType.LAZY)
    protected ApplyInformation applyInformation;

    public static TesterReview newInstance(
            ApplyInformation applyInformation,
            int starPoint,
            String comment
    ) {
        MissionStatus missionStatus = applyInformation.currentTestStatus();
        if (missionStatus != MissionStatus.COMPLETE) {
            throw new ReviewException("Test가 완료 상태가 아닙니다.");
        }
        TesterReview testerReview = new TesterReview();
        testerReview.applyInformation = applyInformation;
        testerReview.registerTime = LocalDateTime.now();
        testerReview.starPoint = starPoint;
        testerReview.comment = comment;
        return testerReview;
    }

    public void connectApplyInformation(ApplyInformation applyInformation) {
        this.applyInformation = applyInformation;
        applyInformation.setTesterReview(this);
    }

}
