package io.wisoft.testermatchingplatform.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Review {

    protected LocalDateTime registerTime;
    protected int starPoint;
    protected String comment;

    @JoinColumn(name = "apply_information_id")
    @OneToOne(fetch = FetchType.LAZY)
    protected ApplyInformation applyInformation;

}
