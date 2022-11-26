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
public class MakerReview extends Review {
    @Id
    @GeneratedValue
    @Column(name="maker_review_id")
    private UUID id;

    public static MakerReview newInstance(
            ApplyInformation applyInformation,
            int starPoint,
            String comment
    ) {
        MakerReview makerReview = new MakerReview();
        makerReview.applyInformation = applyInformation;
        makerReview.registerTime = LocalDateTime.now();
        makerReview.starPoint = starPoint;
        makerReview.comment = comment;
        return makerReview;
    }
}
