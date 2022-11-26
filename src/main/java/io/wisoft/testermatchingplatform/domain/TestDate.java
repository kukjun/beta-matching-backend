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
        return testDate;
    }


}
