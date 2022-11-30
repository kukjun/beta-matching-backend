package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ApproveException;
import io.wisoft.testermatchingplatform.handler.exception.ExecutionException;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class ApplyInformation {
    @Id
    @GeneratedValue
    @Column(name = "apply_information_id")
    private UUID uuid;

    private LocalDateTime registerTime;
    private LocalDateTime approveTime;
    private LocalDateTime executionTime;

    @Enumerated(EnumType.STRING)
    private ApplyInformationStatus status;

    @JoinColumn(name = "test_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tests test;

    @JoinColumn(name = "tester_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Tester tester;

    public static ApplyInformation newInstance(
            Tests test,
            Tester tester
    ) {
        ApplyInformation applyInformation = new ApplyInformation();
        applyInformation.test = test;
        test.addApply();
        applyInformation.tester = tester;
        applyInformation.registerTime = LocalDateTime.now();
        applyInformation.status = ApplyInformationStatus.APPLY;
        return applyInformation;
    }

    public void cancel() {
        test.removeApply();
    }


    public void approve() {
        TestStatus testStatus = TestStatus.refreshStatus(this.test.getTestDate());
        if (testStatus == TestStatus.APPROVE) {
            this.approveTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.APPROVE_SUCCESS;
        } else {
            throw new ApproveException("선정 기간이 아닙니다.");
        }
    }
    public void reject() {
        TestStatus testStatus = TestStatus.refreshStatus(this.test.getTestDate());
        if (testStatus == TestStatus.APPROVE) {
            this.approveTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.APPROVE_FAIL;
            test.getMaker().refundPoint(test.getPoint());
        } else if(testStatus == TestStatus.PROGRESS){
            approveAutomaticFail();
            test.getMaker().refundPoint(test.getPoint());
        } else {
            throw new ApproveException("선정기간, 수행기간이 아닙니다.");
        }
    }

    private void approveAutomaticFail() {
        this.approveTime = test.getTestDate().getRecruitmentTimeEnd().atStartOfDay();
        this.status = ApplyInformationStatus.APPROVE_FAIL;
    }

    public void execute() {
        TestStatus testStatus = TestStatus.refreshStatus(this.test.getTestDate());
        if (testStatus == TestStatus.PROGRESS) {
            this.executionTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.EXECUTE_SUCCESS;
        } else {
            throw new ExecutionException("수행 기간이 아닙니다.");
        }
    }

    public void executeFail() {
        TestStatus testStatus = TestStatus.refreshStatus(this.test.getTestDate());
        if (testStatus == TestStatus.PROGRESS) {
            this.executionTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.EXECUTE_FAIL;
            test.getMaker().refundPoint(test.getPoint());
        } else if(testStatus == TestStatus.COMPLETE) {
            executeAutomaticFail();
            test.getMaker().refundPoint(test.getPoint());
        } else {
            throw new ExecutionException("수행 기간, 완료 기간이 아닙니다.");
        }
    }

    private void executeAutomaticFail() {
        this.executionTime = test.getTestDate().getDurationTimeEnd().atStartOfDay();
        this.status = ApplyInformationStatus.EXECUTE_FAIL;
    }


}
