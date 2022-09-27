package io.wisoft.testermatchingplatform.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 포함
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTime {


    // 생성되어 저장될 때 시간이 자동으로 저장
    @CreatedDate
    private Date registerTime;

}
