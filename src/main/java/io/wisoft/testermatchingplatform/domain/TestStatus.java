package io.wisoft.testermatchingplatform.domain;

import java.time.LocalDate;

public enum TestStatus {
    BEFORE_APPLY, APPLY, APPROVE, PROGRESS, COMPLETE;

    public static TestStatus refreshStatus(TestDate testDate) {
        LocalDate currentTime = LocalDate.now();
        if (currentTime.isBefore(testDate.getRecruitmentTimeStart())) {
            return TestStatus.BEFORE_APPLY;
        } else if (currentTime.isEqual(testDate.getRecruitmentTimeStart()) ||
                currentTime.isBefore(testDate.getRecruitmentTimeEnd()) ||
                currentTime.isEqual(testDate.getRecruitmentTimeEnd())) {
            return TestStatus.APPLY;
        } else if (currentTime.isBefore(testDate.getDurationTimeStart())) {
            return TestStatus.APPROVE;
        } else if (currentTime.isBefore(testDate.getDurationTimeEnd()) ||
                currentTime.isEqual(testDate.getRecruitmentTimeEnd())) {
            return TestStatus.PROGRESS;
        } else {
            return TestStatus.COMPLETE;
        }
    }

}
