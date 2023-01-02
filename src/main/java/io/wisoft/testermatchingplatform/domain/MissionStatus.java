package io.wisoft.testermatchingplatform.domain;

import java.time.LocalDate;

public enum MissionStatus {
    BEFORE_APPLY, APPLY, APPROVE, PROGRESS, COMPLETE;

    // Scheduled 사용하기
    public static MissionStatus refreshStatus(MissionDate missionDate) {
        LocalDate currentTime = LocalDate.now();
        if (currentTime.isBefore(missionDate.getRecruitmentTimeStart())) {
            return MissionStatus.BEFORE_APPLY;
        } else if (currentTime.isEqual(missionDate.getRecruitmentTimeStart()) ||
                currentTime.isBefore(missionDate.getRecruitmentTimeEnd()) ||
                currentTime.isEqual(missionDate.getRecruitmentTimeEnd())) {
            return MissionStatus.APPLY;
        } else if (currentTime.isBefore(missionDate.getDurationTimeStart())) {
            return MissionStatus.APPROVE;
        } else if (currentTime.isBefore(missionDate.getDurationTimeEnd()) ||
                currentTime.isEqual(missionDate.getRecruitmentTimeEnd())) {
            return MissionStatus.PROGRESS;
        } else {
            return MissionStatus.COMPLETE;
        }
    }

}
