package io.wisoft.testermatchingplatform.integration;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.web.controller.MissionController;
import io.wisoft.testermatchingplatform.web.dto.response.DetailMissionResponse;
import io.wisoft.testermatchingplatform.web.dto.response.SimpleMissionListResponse;
import io.wisoft.testermatchingplatform.web.dto.response.Top4MissionListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class MissionControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    private final Gson gson = new Gson();

    @BeforeEach
    public void prepareTest() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("통합 테스트: MissionList 단순 조회 테스트 - 성공")
    public void testListSuccessTest() throws Exception {
        //given

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/missions")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);

    }

    @Test
    @DisplayName("통합 테스트: MissionList 세부 조회 테스트 - 성공")
    public void detailMissionSuccessTest() throws Exception {
        //given
        String expectedStringMissionId = "5c3c4895-8ca6-435a-95f8-487a0784b5c1";

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/missions/")
                        .param("mission_id", expectedStringMissionId)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
        String stringMissionId = JsonPath.parse(jsonResponse).read("$.id");

        assertEquals(expectedStringMissionId, stringMissionId);
    }

    @Test
    @DisplayName("신청상태의 인기있는 게시물 4개 조회 - 성공")
    public void popularTop4SuccessTest() throws Exception {
        //given

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/missions/many_apply")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        List<Integer> applyCounts = JsonPath.parse(jsonResponse).read("$.simpleMissionDTOList[*].apply");

        assertTrue(applyCounts.get(0)>=applyCounts.get(1));
    }

    @Test
    @DisplayName("신청상태의 마감이 가까운 게시물 4개 조회 - 성공")
    public void deadlineTop4SuccessTest() throws Exception {
        //given

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/missions/fast_deadline")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();


        List<Integer> remainDays = JsonPath.parse(jsonResponse).read("$.simpleMissionDTOList[*].deadlineRemain");

        assertTrue(remainDays.get(0)<=remainDays.get(1));

    }

}