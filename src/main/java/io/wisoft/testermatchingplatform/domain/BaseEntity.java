package io.wisoft.testermatchingplatform.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public void createEntity() {
        createDateTime = LocalDateTime.now();
        updateDateTime = LocalDateTime.now();
    }

    public void updateEntity() {
        updateDateTime = LocalDateTime.now();
    }
}
