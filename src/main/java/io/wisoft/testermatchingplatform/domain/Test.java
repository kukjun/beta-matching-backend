package io.wisoft.testermatchingplatform.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Test {

    @Id
    @GeneratedValue
    @Column(name = "test_id")
    private UUID id;

    private String title;
    private String content;
    private String imageURL;
    private long point;

    private int currentApply;
    private int limitApply;

    @Enumerated(EnumType.STRING)
    private TestStatus status;

    @Embedded
    private TestDate testDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maker_id")
    private Maker maker;


    // 생성 시 예외. recruitmentTimeStart가 현재보다 적으면 Error
    public static Test newInstance(
            final String title,
            final String content,
            final String imageURL,
            final long point,
            final int limitApply,
            final Maker maker,
            final LocalDate recruitmentTimeStart,
            final LocalDate recruitmentTimeEnd,
            final LocalDate durationTimeStart,
            final LocalDate durationTimeEnd
    ) {
        if (limitApply <= 0) {
            throw new RuntimeException("제한인원은 0보다 커야 합니다.");
        }
        Test test = new Test();
        test.title = title;
        test.content = content;
        test.imageURL = imageURL;
        test.point = point;
        test.testDate = TestDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
        test.limitApply = limitApply;
        test.currentApply = 0;
        test.maker = maker;
        maker.chargePoint(point * limitApply);
        return test;
    }

    public void addApply() {
        TestStatus testStatus = TestStatus.refreshStatus(testDate);
        if (testStatus == TestStatus.APPLY) {
            currentApply++;
        } else {
            throw new RuntimeException("신청 기간을 초과했습니다.");
        }
    }


}
