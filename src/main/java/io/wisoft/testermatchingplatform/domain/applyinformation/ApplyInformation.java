package io.wisoft.testermatchingplatform.domain.applyinformation;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import io.wisoft.testermatchingplatform.domain.maker.Maker;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "apply_information")
@NoArgsConstructor
public class ApplyInformation extends BaseTime {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy = "uuid")
    @Column(columnDefinition = "BYTEA(16)")
    @NotNull
    private UUID id;

    private Timestamp approveTime;

    private Boolean approveCheck;

    private Timestamp completeTime;

    private Boolean completeCheck;

    @ManyToOne
    @JoinColumn(name = "TEST_ID")
    private Test test;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TESTER_ID")
    private Tester tester;
}
