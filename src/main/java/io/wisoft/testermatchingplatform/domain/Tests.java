package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ApplyException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "test")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Test 가 TestFrameWork와 겹쳐서, Tests로 변경
public class Tests {

    @Id
    @GeneratedValue
    @Column(name = "test_id")
    private UUID id;

    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String imageURL;
    @NotNull
    private long point;
    @NotNull
    private int currentApply;
    @NotNull
    private int limitApply;

    @Enumerated(EnumType.STRING)
    private TestStatus status;

    @Embedded
    private TestDate testDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maker_id")
    private Maker maker;


    // 생성 시 예외. recruitmentTimeStart가 현재보다 적으면 Error
    public static Tests newInstance(
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
            throw new ApplyException("제한인원은 0보다 커야 합니다.");
        }
        Tests test = new Tests();
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
        maker.usePoint(point * limitApply);
        return test;
    }

    public void addApply() {
        TestStatus testStatus = TestStatus.refreshStatus(testDate);
        if (testStatus == TestStatus.APPLY) {
            currentApply++;
        } else {
            throw new ApplyException("신청 기간을 초과했습니다.");
        }
    }


    public void removeApply() {
        TestStatus testStatus = TestStatus.refreshStatus(testDate);
        if (testStatus == TestStatus.APPLY) {
            if (currentApply > 0) {
                currentApply--;
            } else {
                throw new ApplyException("신청인원이 이미 모두 없습니다.");
            }
        } else {
            throw new ApplyException("신청 기간을 초과했습니다.");
        }
    }
}
