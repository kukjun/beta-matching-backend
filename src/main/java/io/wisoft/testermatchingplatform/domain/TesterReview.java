package io.wisoft.testermatchingplatform.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterReview extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "tester_review_id")
    private UUID id;

    private int starPoint;
    private String comment;

    @JoinColumn(name = "apply_information_id", unique = true)
    @OneToOne(fetch = FetchType.LAZY)

    private ApplyInformation applyInformation;

    public static TesterReview newInstance(
            ApplyInformation applyInformation,
            int starPoint,
            String comment
    ) {
        applyInformation.isMissionStatsMatch(MissionStatus.COMPLETE);
        TesterReview testerReview = new TesterReview();
        testerReview.applyInformation = applyInformation;
        testerReview.starPoint = starPoint;
        testerReview.comment = comment;
        testerReview.createEntity();
        return testerReview;
    }


}
