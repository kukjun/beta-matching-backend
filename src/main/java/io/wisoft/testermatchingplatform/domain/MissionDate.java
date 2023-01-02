package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.MissionDateSequenceException;
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
        missionDate.checkTimeSequence();
        missionDate.checkApplyTimeBeforeCurrentTime();
        return missionDate;
    }

    private void checkTimeSequence() {
        if (this.recruitmentTimeStart.isBefore(this.recruitmentTimeEnd)) {
            if (this.recruitmentTimeEnd.isBefore(this.durationTimeStart)) {
                this.durationTimeStart.isBefore(this.durationTimeEnd);
                return;
            }
        }
        throw new MissionDateSequenceException();
    }

    private void checkApplyTimeBeforeCurrentTime() {
        LocalDate localDate = LocalDate.now();
        if (localDate.isAfter(this.recruitmentTimeStart)) {
            throw new MissionDateSequenceException("신청 시작 시간보다 현재 시간이 더 깁니다.");
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
