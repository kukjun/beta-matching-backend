package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ApproveException;
import io.wisoft.testermatchingplatform.handler.exception.ExecutionException;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
public class ApplyInformation extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "apply_information_id")
    private UUID id;

    private LocalDateTime registerTime;
    private LocalDateTime approveTime;
    private LocalDateTime executionTime;

    @Enumerated(EnumType.STRING)
    private ApplyInformationStatus status;

    @JoinColumn(name = "test_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tests test;

    @JoinColumn(name = "tester_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tester tester;

    /**
     * 연관관계 설정 메서드
     */
    public void connectTest(Tests test) {
        this.test = test;
        test.getApplyInformationList().add(this);
    }

    public void connectTester(Tester tester) {
        this.tester = tester;
        tester.getApplyInformationList().add(this);
    }

    public void disconnectTest() {
        test.getApplyInformationList().remove(this);
    }

    public void disconnectTester() {
        tester.getApplyInformationList().remove(this);
    }

    /**
     * 정적 생성자 메서드
     */
    public static ApplyInformation newInstance(
            Tests test,
            Tester tester
    ) {
        ApplyInformation applyInformation = new ApplyInformation();
        applyInformation.connectTest(test);
        applyInformation.connectTester(tester);
        applyInformation.registerTime = LocalDateTime.now();
        applyInformation.status = ApplyInformationStatus.APPLY;
        applyInformation.createEntity();
        return applyInformation;
    }

    /**
     * 비지니스 메서드
     */
    public void applyApprove() {
        TestStatus testStatus = TestStatus.refreshStatus(currentTestDate());
        if (testStatus == TestStatus.APPROVE) {
            this.approveTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.APPROVE_SUCCESS;
        } else {
            throw new ApproveException("선정 기간이 아닙니다.");
        }
    }

    public void applyReject() {
        TestStatus testStatus = TestStatus.refreshStatus(currentTestDate());
        if (testStatus == TestStatus.APPROVE) {
            this.approveTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.APPROVE_FAIL;
            test.getMaker().refundPoint(test.getPoint());
        } else {
            throw new ApproveException("선정기간이 아닙니다.");
        }
    }


    public void executionApprove() {
        TestStatus testStatus = TestStatus.refreshStatus(this.test.getTestDate());
        if (testStatus == TestStatus.PROGRESS) {
            this.executionTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.EXECUTE_SUCCESS;
        } else {
            throw new ExecutionException("수행 기간이 아닙니다.");
        }
    }

    public void executeReject() {
        TestStatus testStatus = TestStatus.refreshStatus(currentTestDate());
        if (testStatus == TestStatus.PROGRESS) {
            this.executionTime = LocalDateTime.now();
            this.status = ApplyInformationStatus.EXECUTE_FAIL;
            test.getMaker().refundPoint(test.getPoint());
        } else {
            throw new ExecutionException("수행 기간, 완료 기간이 아닙니다.");
        }
    }

    private TestDate currentTestDate() {
        return this.test.getTestDate();
    }

    public TestStatus currentTestStatus() {
        return this.test.getStatus();
    }


}
