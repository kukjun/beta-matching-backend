package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tests;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleTestListResponse {
    private final List<SimpleTestDTO> dtoList = new ArrayList<>();


    public static SimpleTestListResponse fromTestList(List<Tests> list) {

        SimpleTestListResponse response = new SimpleTestListResponse();
        for (Tests test : list) {
            SimpleTestDTO dto = SimpleTestDTO.fromTest(test);
            response.dtoList.add(dto);
        }
        return response;
    }
}
