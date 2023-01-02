package io.wisoft.testermatchingplatform.web.dto.response;

import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.web.dto.SimpleTestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleMissionListResponse {
    private final List<SimpleTestDTO> dtoList = new ArrayList<>();


    public static SimpleMissionListResponse fromMissionList(List<Mission> list) {

        SimpleMissionListResponse response = new SimpleMissionListResponse();
        for (Mission test : list) {
            SimpleTestDTO dto = SimpleTestDTO.fromTest(test);
            response.dtoList.add(dto);
        }
        return response;
    }
}
