package io.wisoft.testermatchingplatform.domain.testerreview;

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
@Table(name = "tester_review")
@NoArgsConstructor
public class TesterReview extends BaseTime {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy = "uuid")
    @Column(columnDefinition = "BYTEA(16)")
    @NotNull
    private UUID id;

    @NotNull
    private int starPoint;

    @NotNull
    private String comment;

    @NotNull
    @JoinColumn(name = "APPLY_INFORMATION_ID")
    @ManyToOne
    private ApplyInformation applyInformation;
}
