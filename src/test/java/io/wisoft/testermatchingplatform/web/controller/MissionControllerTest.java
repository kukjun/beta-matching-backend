package io.wisoft.testermatchingplatform.web.controller;

import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.web.dto.response.DetailMissionResponse;
import io.wisoft.testermatchingplatform.web.dto.response.SimpleMissionListResponse;
import io.wisoft.testermatchingplatform.web.dto.response.Top4MissionListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MissionController.class)
class MissionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MissionService missionService;

    @Test
    @DisplayName("MissionList 단순 조회 테스트 - 성공")
    public void testListSuccessTest() throws Exception {
        //given
        SimpleMissionListResponse response = mock(SimpleMissionListResponse.class);
        when(missionService.applyMissionList())
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                get("/missions")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("MissionList 단순 조회 테스트 - 성공")
    public void detailMissionSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        DetailMissionResponse response = mock(DetailMissionResponse.class);
        when(missionService.detailMission(any(UUID.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                get("/missions/")
                        .param("mission_id", missionId.toString())
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("신청상태의 인기있는 게시물 4개 조회 - 성공")
    public void popularTop4SuccessTest() throws Exception {
        //given
        Top4MissionListResponse response = mock(Top4MissionListResponse.class);
        when(missionService.top4Popular())
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                get("/missions/many_apply")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("신청상태의 마감이 가까운 게시물 4개 조회 - 성공")
    public void deadlineTop4SuccessTest() throws Exception {
        //given
        Top4MissionListResponse response = mock(Top4MissionListResponse.class);
        when(missionService.top4Deadline())
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                get("/missions/fast_deadline")
        ).andExpect(
                status().isOk()
        );
    }

}