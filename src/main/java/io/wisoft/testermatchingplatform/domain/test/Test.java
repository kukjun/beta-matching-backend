package io.wisoft.testermatchingplatform.domain.test;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import io.wisoft.testermatchingplatform.domain.maker.Maker;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "test")
@NoArgsConstructor
@NamedEntityGraph(name = "TestWithMaker", attributeNodes = @NamedAttributeNode("maker"))
public class Test extends BaseTime {
    public enum Period {
        BEFORE_RECRUITMENT, RECRUITMENT, AFTER_RECRUITMENT, PROGRESS, COMPLETE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String title;

    private int participantCapacity;

    @Embedded
    private TestRelateTime testRelateTime;

    private String content;

    private int reward;

    private String symbolImageRoot;

    @LastModifiedDate
    private Timestamp modifyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAKER_ID")
    private Maker maker;

    public Test(UUID id) {
        this.id = id;
    }

    // 두 날짜의 차이를 구하기 위한 Method, Date를 사용하고 있으므로, 직접 계산함
    public Long calculateDeadlineRemain() {
        // Calendar 사용

        Calendar current = Calendar.getInstance();
        current.setTime(new Date());

        Calendar recruitmentLimit = Calendar.getInstance();
        recruitmentLimit.setTime(testRelateTime.getRecruitmentTimeLimit());

        return (recruitmentLimit.getTimeInMillis() - current.getTimeInMillis()) / (1000 * 24 * 60 * 60);
    }

    public Period getPeriod() {
        Date date = new Date();
        if (date.getTime() <= testRelateTime.getRecruitmentTimeStart().getTime())
            return Period.BEFORE_RECRUITMENT;
        else if (date.getTime() <= testRelateTime.getRecruitmentTimeLimit().getTime())
            return Period.RECRUITMENT;
        else if (date.getTime() <= testRelateTime.getDurationTimeStart().getTime())
            return Period.AFTER_RECRUITMENT;
        else if (date.getTime() <= testRelateTime.getDurationTimeLimit().getTime())
            return Period.PROGRESS;
        else
            return Period.COMPLETE;
    }

}
