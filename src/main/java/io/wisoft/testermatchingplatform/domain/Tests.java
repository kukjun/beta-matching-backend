package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ApplyException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "test")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// Test 가 TestFrameWork와 겹쳐서, Tests로 변경
public class Tests extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "test_id")
    private UUID id;

    private String title;
    private String content;
    private String imageURL;
    private long reward;
    private int limitPerformer;

    @Enumerated(EnumType.STRING)
    private TestStatus status;

    @Embedded
    private TestDate testDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maker_id")
    private Maker maker;

    @OneToMany(mappedBy = "test")
    private List<ApplyInformation> applyInformationList = new ArrayList<>();


    /**
     * 연관관계 편의 메서드
     */
    public void connectCreatedMaker(Maker maker) {
        this.maker = maker;
        maker.getCreatedTests().add(this);
    }

    public void disconnectCreateMaker(Maker maker) {
        maker.getCreatedTests().remove(this);
    }

    /**
     * 정적 생성자 메서드
     */

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
        test.reward = point;
        test.testDate = TestDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
        test.limitPerformer = limitApply;
        test.maker = maker;
        maker.usePoint(point * limitApply);
        test.createEntity();
        return test;
    }

    /**
     * 비지니스 메서드
     */

    public void updateTest(
            final String title,
            final String content,
            final String imageURL,
            final long reward,
            final int limitPerformer,
            final LocalDate recruitmentTimeStart,
            final LocalDate recruitmentTimeEnd,
            final LocalDate durationTimeStart,
            final LocalDate durationTimeEnd
    ) {
        if (limitPerformer <= 0) {
            throw new ApplyException("제한인원은 0보다 커야 합니다.");
        }
        this.maker.updatePoint(reward * limitPerformer - this.reward * this.limitPerformer);
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
        this.reward = reward;
        this.testDate = TestDate.newInstance(
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
        this.limitPerformer = limitPerformer;
        updateEntity();
    }
}
