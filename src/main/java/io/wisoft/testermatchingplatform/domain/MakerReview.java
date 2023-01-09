package io.wisoft.testermatchingplatform.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MakerReview extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "maker_review_id")
    private UUID id;
    private int starPoint;
    private String comment;

    @JoinColumn(name = "apply_information_id")
    @OneToOne(fetch = FetchType.LAZY)
    private ApplyInformation applyInformation;

    public static MakerReview newInstance(
            ApplyInformation applyInformation,
            int starPoint,
            String comment
    ) {
        applyInformation.isMissionStatsMatch(MissionStatus.COMPLETE);
        MakerReview makerReview = new MakerReview();
        makerReview.applyInformation = applyInformation;
        makerReview.starPoint = starPoint;
        makerReview.comment = comment;
        makerReview.createEntity();
        return makerReview;
    }

}
