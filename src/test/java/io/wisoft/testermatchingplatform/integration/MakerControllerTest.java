package io.wisoft.testermatchingplatform.integration;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.wisoft.testermatchingplatform.jwt.JwtProvider;
import io.wisoft.testermatchingplatform.service.ApplyInformationService;
import io.wisoft.testermatchingplatform.service.MakerService;
import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.service.TesterReviewService;
import io.wisoft.testermatchingplatform.web.controller.MakerController;
import io.wisoft.testermatchingplatform.web.dto.request.*;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class MakerControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    private final Gson gson = new Gson();

    @Autowired
    private JwtProvider jwtProvider;

    @BeforeEach
    public void prepareTest() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    // Test 생성, 수정에 대해서는 MultipartForm의 형태로 request를 만드는 법을 몰라서 Pass

    @Test
    @DisplayName("통합 테스트: 만든 missionList를 조회 테스트 - 성공")
    public void findMadeMissionList() throws Exception {
        //given
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");
        //when
        //then
        String jsonResponse = mvc.perform(
                get("/makers/" + makerId + "/missions")
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);

    }


    @Test
    @DisplayName("통합 테스트: mission을 신청한 인원들 조회 테스트 - 성공")
    public void findApplyTestSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c1");
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");
        //when
        //then
        String jsonResponse = mvc.perform(
                get("/makers/missions/" + missionId + "/apply")
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);

    }

    @Test
    @DisplayName("통합 테스트: mission을 수행하는 인원들 조회 테스트 - 성공")
    public void findPerformTestersSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c3");
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");
        //when
        //then
        String jsonResponse = mvc.perform(
                get("/makers/missions/" + missionId + "/perform")
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
    }

    @Test
    @DisplayName("통합 테스트: mission을 완료한 인원들 조회 테스트 - 성공")
    public void findCompleteTestersSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c4");
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");
        //when
        //then
        String jsonResponse = mvc.perform(
                get("/makers/missions/" + missionId + "/perform/review")
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
    }

    @Test
    @DisplayName("통합 테스트: 교환하기 위한 화면 정보 조회 테스트 - 성공")
    public void exchangeViewSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");
        long expectedPoint = 177856L;
        String expectedAccount = "52820204064486";
        //when
        //then
        String jsonResponse = mvc.perform(
                get("/makers/" + makerId + "/exchange")
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
        int intPoint = JsonPath.parse(jsonResponse).read("$.point");
        long point = (long) intPoint;
        String account = JsonPath.parse(jsonResponse).read("$.account");

        assertEquals(expectedAccount, account);
        assertEquals(expectedPoint, point);

    }


    @Test
    @DisplayName("통합 테스트: 계좌 정보 업데이트를 위한 테스트 - 성공")
    public void updateAccountSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String expectedAccount = "1275-43178521-5124";
        AccountRequest request = AccountRequest.newInstance(
                expectedAccount
        );
        String jsonRequest = gson.toJson(request);
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");
        //when
        //then
        String jsonResponse = mvc.perform(
                patch("/makers/" + makerId + "/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        String account = JsonPath.parse(jsonResponse).read("$.account");
        assertEquals(expectedAccount, account);

    }

    @Test
    @DisplayName("통합 테스트: Point를 Cash로 전환하는 Test - 성공")
    public void changePointToCashSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");
        long requestPoint = 5000L;
        ChangePointToCashRequest request = ChangePointToCashRequest.newInstance(
                requestPoint
        );
        long expectedCash = requestPoint * 19 / 20;
        String jsonRequest = gson.toJson(request);

        //when
        //then
        String jsonResponse = mvc.perform(
                post("/makers/" + makerId + "/exchange/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        int intCash = JsonPath.parse(jsonResponse).read("cash");
        long cash = (long) intCash;

        assertEquals(expectedCash, cash);

    }

    @Test
    @DisplayName("통합 테스트: Cash를 Point로 전환하는 Test - 성공")
    public void changeCashToPointSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");
        long requestCash = 50000L;
        ChangeCashToPointRequest request = ChangeCashToPointRequest.newInstance(
                requestCash
        );
        long expectedPoint = requestCash;
        String jsonRequest = gson.toJson(request);

        //when
        //then
        String jsonResponse = mvc.perform(
                post("/makers/" + makerId + "/exchange/cash")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        int intPoint = JsonPath.parse(jsonResponse).read("point");
        long point = (long) intPoint;
        assertEquals(expectedPoint, point);

    }

    @Test
    @DisplayName("통합 테스트: 신청한 Tester, 수행으로 변경하는 Test - 성공")
    public void changeApplyToPerformSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c2");
        List<UUID> approvedTesterList = new ArrayList<>();
        approvedTesterList.add(UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1"));
        approvedTesterList.add(UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b2"));
        ChangeApplyToApproveRequest request = ChangeApplyToApproveRequest.newInstance(
                approvedTesterList
        );
        List<UUID> expectedApplyInformationList = new ArrayList<>();
        expectedApplyInformationList.add(UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5d4"));
        expectedApplyInformationList.add(UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5d5"));

        String jsonRequest = gson.toJson(request);
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");

        //when
        //then
        String jsonResponse = mvc.perform(
                post("/makers/missions/" + missionId + "/perform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();

        System.out.println("jsonResponse = " + jsonResponse);
        List<String> approveIdList = JsonPath.parse(jsonResponse).read("$.successApplyInformationIdList[*]");

        assertEquals(expectedApplyInformationList.get(0).toString(), approveIdList.get(0));
        assertEquals(expectedApplyInformationList.get(1).toString(), approveIdList.get(1));
    }

    @Test
    @DisplayName("통합 테스트: 수행한 Tester, 완료로 변경하는 Test - 성공")
    public void changePerformToCompleteSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c3");
        List<UUID> executeTesterList = new ArrayList<>();
        executeTesterList.add(UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1"));
        executeTesterList.add(UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b2"));
        ChangePerformToCompleteRequest request = ChangePerformToCompleteRequest.newInstance(
                executeTesterList
        );
        List<UUID> expectedApplyInformationList = new ArrayList<>();
        expectedApplyInformationList.add(UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5d8"));
        expectedApplyInformationList.add(UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5d9"));

        String jsonRequest = gson.toJson(request);
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        String accessToken = jwtProvider.createJwtAccessToken(makerId, "maker");

        //when
        //then
        String jsonResponse = mvc.perform(
                post("/makers/missions/" + missionId + "/complete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("AUTHORIZATION", "Bearer " + accessToken)
        ).andExpect(
                status().isOk()
        ).andExpect(
                header().exists("AUTHORIZATION")
        ).andReturn().getResponse().getContentAsString();
        System.out.println("jsonResponse = " + jsonResponse);
        List<String> executedIdList = JsonPath.parse(jsonResponse).read("$.completeTesterIdList[*]");

        assertEquals(expectedApplyInformationList.get(0).toString(), executedIdList.get(0));
        assertEquals(expectedApplyInformationList.get(1).toString(), executedIdList.get(1));

    }

    // TestCase 부족
//    @Test
//    @DisplayName("통합 테스트: 완료 Mission을 수행한 Tester 리뷰 작성 테스트 - 성공")
//    public void createReviewSuccessTest() throws Exception {
//        //given
//        UUID makerId = UUID.randomUUID();
//        CreateTesterReviewListRequest request = mock(CreateTesterReviewListRequest.class);
//        String jsonRequest = gson.toJson(request);
//        CreateTesterReviewListResponse response = mock(CreateTesterReviewListResponse.class);
//
//        //when
//        //then
//        mvc.perform(
//                post("/makers/" + makerId + "/missions/perform/review")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest)
//        ).andExpect(
//                status().isOk()
//        );
//    }


}