package io.wisoft.testermatchingplatform.domain.makerreview;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "maker_review")
@NoArgsConstructor
public class MakerReview extends BaseTime {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy = "uuid")
    @Column(columnDefinition = "BINARY(16)")
    @NotNull
    private String id;

    private int starPoint;

    private String comment;

    @JoinColumn(name = "APPLY_INFORMATION_ID")
    @ManyToOne
    private ApplyInformation applyInformation;
}
