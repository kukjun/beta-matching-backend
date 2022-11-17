package io.wisoft.testermatchingplatform.web.dto.response.tester.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PopularDto {
    private int count;
    private UUID id;

}
