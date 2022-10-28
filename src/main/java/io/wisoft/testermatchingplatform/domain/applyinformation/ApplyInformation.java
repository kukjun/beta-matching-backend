package io.wisoft.testermatchingplatform.domain.applyinformation;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.tester.Tester;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "apply_information")
@NoArgsConstructor
@NamedEntityGraph(name = "ApplyInfoWithTest", attributeNodes = @NamedAttributeNode("test"))
public class ApplyInformation extends BaseTime {


    public enum Status {
        APPLY, APPROVE, PROGRESS, NOT_COMPLETE, COMPLETE, NOTHING
    }


    public Status getStatus() {
        Date date = new Date();
        if (date.getTime() >= test.getTestRelateTime().getRecruitmentTimeStart().getTime()
                && date.getTime() <= test.getTestRelateTime().getRecruitmentTimeLimit().getTime()
                && !approveCheck)
            return Status.APPLY;
        else if (date.getTime() >= test.getTestRelateTime().getRecruitmentTimeStart().getTime()
                && date.getTime() <= test.getTestRelateTime().getDurationTimeStart().getTime()
                && approveCheck)
            return Status.APPROVE;
        else if (date.getTime() >= test.getTestRelateTime().getDurationTimeStart().getTime()
                && date.getTime() <= test.getTestRelateTime().getRecruitmentTimeLimit().getTime()
                && approveCheck && completeTime == null)
            return Status.PROGRESS;
        else if (date.getTime() >= test.getTestRelateTime().getDurationTimeStart().getTime()
                && approveCheck && completeTime != null && !completeCheck)
            return Status.NOT_COMPLETE;
        else if (date.getTime() >= test.getTestRelateTime().getDurationTimeStart().getTime()
                && approveCheck && completeTime != null && completeCheck)
            return Status.COMPLETE;
        else return Status.NOTHING;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public ApplyInformation(UUID id) {
        this.id = id;
    }


    public ApplyInformation(Test test, Tester tester, boolean approveCheck, boolean completeCheck) {
        this.test = test;
        this.tester = tester;
        this.approveCheck = approveCheck;
        this.completeCheck = completeCheck;
    }
}
