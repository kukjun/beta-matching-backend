package io.wisoft.testermatchingplatform.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TesterReview extends Review {
    @Id
    @GeneratedValue
    @Column(name = "tester_review_id")
    private UUID id;

    public static TesterReview newInstance(
            ApplyInformation applyInformation,
            int starPoint,
            String comment
    ) {
        TesterReview testerReview = new TesterReview();
        testerReview.applyInformation = applyInformation;
        testerReview.registerTime = LocalDateTime.now();
        testerReview.starPoint = starPoint;
        testerReview.comment = comment;
        return testerReview;
    }

}
