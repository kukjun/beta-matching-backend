package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.TestDateSequenceException;
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
        throw new TestDateSequenceException();
    }

    private void checkApplyTimeBeforeCurrentTime() {
        LocalDate localDate = LocalDate.now();
        if (localDate.isAfter(this.recruitmentTimeStart)) {
            throw new TestDateSequenceException("신청 시작 시간보다 현재 시간이 더 깁니다.");
        }
    }

    public long remainApplyTime() {
        LocalDate currentTime = LocalDate.now();
        long currentDay = currentTime.toEpochDay();
        long applyLimitDay = recruitmentTimeEnd.toEpochDay();
        long remainDay = applyLimitDay - currentDay;
        if (remainDay >= 0) {
            return remainDay;
        } else {
            return -1;
        }

    }


}
