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
            final Maker maker,
            final LocalDate recruitmentTimeStart,
            final LocalDate recruitmentTimeEnd,
            final LocalDate durationTimeStart,
            final LocalDate durationTimeEnd
    ) {
        LocalDate currentTime = LocalDate.now();

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
        test.maker = maker;
        return test;
    }

    public void refreshStatus(LocalDate currentTime) {
        if (currentTime.isBefore(testDate.getRecruitmentTimeStart())) {
            this.status = TestStatus.BEFORE_APPLY;
        } else if (currentTime.isEqual(testDate.getRecruitmentTimeStart()) ||
                currentTime.isBefore(testDate.getRecruitmentTimeEnd()) ||
                currentTime.isEqual(testDate.getRecruitmentTimeEnd())) {
            this.status = TestStatus.APPLY;
        } else if (currentTime.isBefore(testDate.getDurationTimeStart())) {
            this.status = TestStatus.APPROVE;
        } else if (currentTime.isBefore(testDate.getDurationTimeEnd()) ||
                currentTime.isEqual(testDate.getRecruitmentTimeEnd())) {
            this.status = TestStatus.PROGRESS;
        } else if (currentTime.isAfter(testDate.getDurationTimeEnd())) {
            this.status = TestStatus.COMPLETE;
        }
    }


}
