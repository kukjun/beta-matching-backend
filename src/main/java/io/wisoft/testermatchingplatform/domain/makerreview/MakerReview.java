package io.wisoft.testermatchingplatform.domain.makerreview;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import io.wisoft.testermatchingplatform.domain.applyinformation.ApplyInformation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@Table(name = "maker_review")
@NoArgsConstructor
public class MakerReview extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private int starPoint;

    private String comment;

    @JoinColumn(name = "APPLY_INFORMATION_ID")
    @ManyToOne
    private ApplyInformation applyInformation;
}
