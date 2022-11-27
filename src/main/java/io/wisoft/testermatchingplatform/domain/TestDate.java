package io.wisoft.testermatchingplatform.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestDate {

    private LocalDate recruitmentTimeStart;
    private LocalDate recruitmentTimeEnd;
    private LocalDate durationTimeStart;
    private LocalDate durationTimeEnd;

    public static TestDate newInstance(
            LocalDate recruitmentTimeStart,
            LocalDate recruitmentTimeEnd,
            LocalDate durationTimeStart,
            LocalDate durationTimeEnd
    ) {
        TestDate testDate = new TestDate();
        testDate.recruitmentTimeStart = recruitmentTimeStart;
        testDate.recruitmentTimeEnd = recruitmentTimeEnd;
        testDate.durationTimeStart = durationTimeStart;
        testDate.durationTimeEnd = durationTimeEnd;
        testDate.checkTimeSequence();
        testDate.checkApplyTimeBeforeCurrentTime();
        return testDate;
    }

    private void checkTimeSequence() {
        if (this.recruitmentTimeStart.isBefore(this.recruitmentTimeEnd)) {
            if (this.recruitmentTimeEnd.isBefore(this.durationTimeStart)) {
                this.durationTimeStart.isBefore(this.durationTimeEnd);
                return;
            }
        }
        throw new RuntimeException("시간 순서대로 입력을 받지 못했습니다.");
    }

    public void checkApplyTimeBeforeCurrentTime() {
        LocalDate localDate = LocalDate.now();
        if (!localDate.isBefore(this.recruitmentTimeStart)) {
            throw new RuntimeException("신청 시작 시간보다 현재 시간이 더 깁니다.");
        }
    }


}
