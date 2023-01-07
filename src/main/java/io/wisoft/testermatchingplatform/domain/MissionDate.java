package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.MissionDateMisMatchException;
import io.wisoft.testermatchingplatform.handler.exception.domain.MissionDateCurrentTimeBeforeApplyException;
import io.wisoft.testermatchingplatform.handler.exception.domain.NotApplyPeriodException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionDate {

    private LocalDate recruitmentTimeStart;
    private LocalDate recruitmentTimeEnd;
    private LocalDate durationTimeStart;
    private LocalDate durationTimeEnd;

    /**
     * 정적 생성자 로직
     */
    public static MissionDate newInstance(
            LocalDate recruitmentTimeStart,
            LocalDate recruitmentTimeEnd,
            LocalDate durationTimeStart,
            LocalDate durationTimeEnd
    ) {
        MissionDate missionDate = new MissionDate();
        missionDate.recruitmentTimeStart = recruitmentTimeStart;
        missionDate.recruitmentTimeEnd = recruitmentTimeEnd;
        missionDate.durationTimeStart = durationTimeStart;
        missionDate.durationTimeEnd = durationTimeEnd;
        missionDate.isValidTimeSequence();
        missionDate.isValidTimeAfterCurrentTime();
        return missionDate;
    }

    /**
     * 비지니스 로직
     */
    public long remainApplyTime() {
        isValidApplyPeriod();
        long applyLimitDay = recruitmentTimeEnd.toEpochDay();
        long currentDay = LocalDate.now().toEpochDay();

        long remainDay = applyLimitDay - currentDay;
            return remainDay;
    }

    /**
     * 예외 처리 로직
     */
    private void isValidTimeSequence() {
        if (this.recruitmentTimeStart.isBefore(this.recruitmentTimeEnd)) {
            if (this.recruitmentTimeEnd.isBefore(this.durationTimeStart)) {
                this.durationTimeStart.isBefore(this.durationTimeEnd);
                return;
            }
        }
        throw new MissionDateMisMatchException();
    }
    private void isValidTimeAfterCurrentTime() {
        LocalDate localDate = LocalDate.now();
        if (localDate.isAfter(this.recruitmentTimeStart)) {
            throw new MissionDateCurrentTimeBeforeApplyException();
        }
    }

    private void isValidApplyPeriod() {
        long currentDay = LocalDate.now().toEpochDay();
        long applyLimitDay = recruitmentTimeEnd.toEpochDay();
        long applyStartDay = recruitmentTimeStart.toEpochDay();

        if (currentDay < applyStartDay || currentDay > applyLimitDay) {
            throw new NotApplyPeriodException(
                    "ApplyStart: " + recruitmentTimeStart
                            + "\nApplyEnd: " + recruitmentTimeEnd
            );
        }
    }




}
