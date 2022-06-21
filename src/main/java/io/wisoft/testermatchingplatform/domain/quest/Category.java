package io.wisoft.testermatchingplatform.domain.quest;

import lombok.Getter;

@Getter
public class Category {

    private Long id;
    private String name;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
