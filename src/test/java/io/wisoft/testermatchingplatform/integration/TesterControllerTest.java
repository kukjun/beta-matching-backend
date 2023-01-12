package io.wisoft.testermatchingplatform.integration;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.wisoft.testermatchingplatform.service.ApplyInformationService;
import io.wisoft.testermatchingplatform.service.MakerReviewService;
import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.service.TesterService;
import io.wisoft.testermatchingplatform.web.controller.TesterController;
import io.wisoft.testermatchingplatform.web.dto.request.AccountRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ApplyMissionRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePointToCashRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerReviewRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class TesterControllerTest {

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
    @DisplayName("통합 테스트: 신청 MissionList 조회")
    public void findApplyMissionSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b2");

        int expectedAppliedListSize = 2;
        String stringExpectedApprovedId = "5c3c4895-8ca6-435a-95f8-487a0784b5c3";
        String stringExpectedExecutedId = "5c3c4895-8ca6-435a-95f8-487a0784b5c4";

        //when
        //then
        MvcResult result = mvc.perform(get("/testers/" + testerId + "/apply"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<String> AppliedList = JsonPath.parse(content).read("$.appliedMissionDTOList[*]");
        String stringApprovedId = JsonPath.parse(content).read("$.approvedMissionDTOList[0].id");
        String stringExecutedId = JsonPath.parse(content).read("$.executedMissionDTOList[0].id");

        assertEquals(expectedAppliedListSize, AppliedList.size());
        assertEquals(stringExpectedApprovedId, stringApprovedId);
        assertEquals(stringExpectedExecutedId, stringExecutedId);
    }

    @Test
    @DisplayName("통합 테스트: Mission 신청 하기 - 성공")
    public void findMissionSuccessTest() throws Exception {
        //given
        ApplyMissionRequest request = ApplyMissionRequest.newInstance(
                UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5f1")
        );

        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b3");
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5f1");
        String jsonRequest = gson.toJson(request);

        //when
        //then
        mvc.perform(
                post("/testers/" + testerId + "/missions/" + missionId + "/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    @DisplayName("통합 테스트: Mission 신청 취소하기 - 성공")
    public void cancelApplySuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b2");
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5f0");

        //when
        //then
        mvc.perform(
                delete("/testers/" + testerId + "/missions/" + missionId + "/apply")
        ).andExpect(
                status().is2xxSuccessful()
        );

    }

    @Test
    @DisplayName("통합 테스트: ApplyInformationId를 검색하는 테스트 - 성공")
    public void findApplyIdSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c1");
        UUID expectedApplyInformationId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5d0");

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/testers/" + testerId + "/missions/" + missionId)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();
        String response = jsonResponse.replaceAll("\"", "");

        assertEquals(expectedApplyInformationId.toString(), response);


    }


    @Test
    @DisplayName("통합 테스트: MissioList을 남은 기간 순으로 조회하는 API")
    public void findTestListByDeadlineSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/testers/" + testerId + "/missions/deadline")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
        List<Integer> remainDays = JsonPath.parse(jsonResponse).read("$.dtoList[*].deadlineRemain");

        assertTrue(remainDays.get(0) <= remainDays.get(1));

    }

    @Test
    @DisplayName("통합 테스트: MissioList를 인기순으로 조회하는 API")
    public void findTestListByPopularSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/testers/" + testerId + "/missions/popular")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        List<Integer> applyCounts = JsonPath.parse(jsonResponse).read("$.dtoList[*].apply");

        assertTrue(applyCounts.get(0) >= applyCounts.get(1));
    }

    @Test
    @DisplayName("통합 테스트: MissioList를 생성순으로 조회하는 API")
    public void findTestListByCreatedSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/testers/" + testerId + "/missions/created")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        // 2022-12-01 15:00:00.000000 +00:00
        // 2023-01-01 15:00:00.000000 +00:00 순서임
    }


    @Test
    @DisplayName("통합 테스트: MissionList를 기본으로 조회하는 API")
    public void findTestListByNormalSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");


        //when
        //then
        mvc.perform(
                get("/testers/" + testerId + "/missions")
        ).andExpect(
                status().isOk()
        );
    }


    @Test
    @DisplayName("리뷰 생성 테스트 - 성공")
    public void createReviewSuccessTest() throws Exception {
        //given
        UUID applyInformationId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5e5");
        CreateMakerReviewRequest request = CreateMakerReviewRequest.newInstance(
                applyInformationId,
                5, "good"
        );
        String jsonRequest = gson.toJson(request);


        //when
        //then
        String jsonResponse = mvc.perform(
                post("/testers/apply/" + applyInformationId + "/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
    }


    @Test
    @DisplayName("통합 테스트: 교환하기 위한 화면 정보 조회 테스트 - 성공")
    public void exchangeViewSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        long expectPoint = 5000L;
        String expectAccount = "2";

        //when
        //then
        String jsonResponse = mvc.perform(
                get("/testers/" + testerId + "/exchange")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        int intPoint = JsonPath.parse(jsonResponse).read("$.point");
        long point = (long) intPoint;
        String account = JsonPath.parse(jsonResponse).read("$.account");

        assertEquals(expectPoint, point);
        assertEquals(expectAccount, account);

    }

    @Test
    @DisplayName("통합 테스트: 계좌 정보 업데이트를 위한 테스트 - 성공")
    public void updateAccountSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        String expectedAccount = "1275-43178521-5124";
        AccountRequest request = AccountRequest.newInstance(
                expectedAccount
        );
        String jsonRequest = gson.toJson(request);

        //when
        //then
        String jsonResponse = mvc.perform(
                patch("/testers/" + testerId + "/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
        String account = JsonPath.parse(jsonResponse).read("$.account");
        System.out.println("account = " + account);

        assertEquals(expectedAccount, account);

    }

    @Test
    @DisplayName("통합 테스트: Point를 Cash로 전환하는 Test - 성공")
    public void changePointToCashSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        long requestPoint = 5000L;
        ChangePointToCashRequest request = ChangePointToCashRequest.newInstance(
                requestPoint
        );
        long expectedCash = requestPoint * 19 / 20;
        String jsonRequest = gson.toJson(request);

        //when
        //then
        String jsonResponse = mvc.perform(
                post("/testers/" + testerId + "/exchange/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
        int intCash = JsonPath.parse(jsonResponse).read("cash");
        long cash = (long) intCash;


        assertEquals(expectedCash, cash);

    }


}