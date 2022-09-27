package io.wisoft.testermatchingplatform.domain.test;

import io.wisoft.testermatchingplatform.domain.BaseTime;
import io.wisoft.testermatchingplatform.domain.maker.Maker;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "test")
@NoArgsConstructor
public class Test extends BaseTime {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid")
    @Column(columnDefinition = "BINARY(16)")
    @NotNull
    private String id;
    @Column(unique = true)
    @NotNull
    private String title;

    @NotNull
    private int participantCapacity;
    @Embedded
    private TestRelateTime testRelateTime;

    @NotNull
    private String content;

    @NotNull
    private int reward;

    private String symbolImageRoot;

    @NotNull
    @LastModifiedDate
    private Timestamp modifyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAKER_ID")
    private Maker maker;
}
