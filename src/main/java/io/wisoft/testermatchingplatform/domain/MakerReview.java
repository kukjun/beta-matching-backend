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
public class MakerReview extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="maker_review_id")
    private UUID id;
    private LocalDateTime registerTime;
    private int starPoint;
    private String comment;

    @JoinColumn(name = "apply_information_id")
    @OneToOne(fetch = FetchType.LAZY)
    protected ApplyInformation applyInformation;

    public static MakerReview newInstance(
            ApplyInformation applyInformation,
            int starPoint,
            String comment
    ) {
        MissionStatus missionStatus = applyInformation.currentTestStatus();
        if (missionStatus == MissionStatus.COMPLETE) {
            MakerReview makerReview = new MakerReview();
            makerReview.applyInformation = applyInformation;
            makerReview.registerTime = LocalDateTime.now();
            makerReview.starPoint = starPoint;
            makerReview.comment = comment;
            return makerReview;
        }
        else {
            throw new ReviewException("Test가 완료 상태가 아닙니다.");
        }
    }

    public void connectApplyInformation(ApplyInformation applyInformation) {
        this.applyInformation = applyInformation;
        applyInformation.setMakerReview(this);
    }
}
