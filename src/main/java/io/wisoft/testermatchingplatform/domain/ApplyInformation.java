package io.wisoft.testermatchingplatform.domain;

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
    private Test test;

    @JoinColumn(name = "tester_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Tester tester;

    public static ApplyInformation newInstance(
            Test test,
            Tester tester
    ) {
        ApplyInformation applyInformation = new ApplyInformation();
        applyInformation.test = test;
        applyInformation.tester = tester;
        applyInformation.registerTime = LocalDateTime.now();
        applyInformation.status = ApplyInformationStatus.APPLY;
        return applyInformation;
    }

    public void approve() {
        this.approveTime = LocalDateTime.now();
        this.status = ApplyInformationStatus.APPROVE;
    }

    public void execute() {
        this.executionTime = LocalDateTime.now();
        this.status = ApplyInformationStatus.SUCCESS;
    }

    public void fail(LocalDateTime durationTimeEnd) {
        this.executionTime = durationTimeEnd;
        this.status = ApplyInformationStatus.FAIL;
    }

    // 시간이 있어서 생각하기가 어렵다..? Test Logic으로 넘어가야함.



}
