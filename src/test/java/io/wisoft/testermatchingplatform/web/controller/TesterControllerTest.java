package io.wisoft.testermatchingplatform.web.controller;

import com.google.gson.Gson;
import io.wisoft.testermatchingplatform.service.ApplyInformationService;
import io.wisoft.testermatchingplatform.service.MakerReviewService;
import io.wisoft.testermatchingplatform.service.MissionService;
import io.wisoft.testermatchingplatform.service.TesterService;
import io.wisoft.testermatchingplatform.web.dto.request.*;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TesterController.class)
class TesterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TesterService testerService;
    @MockBean
    private MissionService missionService;
    @MockBean
    private ApplyInformationService applyInformationService;
    @MockBean
    private MakerReviewService makerReviewService;

    private final Gson gson = new Gson();

    @Test
    @DisplayName("신청 Mission 조회")
    public void findApplyMissionSuccessTest() throws Exception {
        //given
        when(missionService.applyMissionListFromTester(UUID.randomUUID()))
                .thenReturn(mock(ApplyMissionListFromTesterResponse.class));
        UUID testerId = UUID.randomUUID();
        //when
        //then
        mvc.perform(get("/testers/" + testerId + "/apply"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Mission 신청 하기 - 성공")
    public void findMissionSuccessTest() throws Exception {
        //given
        when(applyInformationService.applyMission(any(UUID.class), any(UUID.class)))
                .thenReturn(mock(ApplyMissionResponse.class));

        UUID testerId = UUID.randomUUID();
        UUID missionId = UUID.randomUUID();
        ApplyMissionRequest request = mock(ApplyMissionRequest.class);
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
    @DisplayName("Mission 신청 취소하기 - 성공")
    public void cancelApplySuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();
        UUID missionId = UUID.randomUUID();

        //when
        //then
        mvc.perform(
                delete("/testers/" + testerId + "/missions/" + missionId + "/apply")
        ).andExpect(
                status().is2xxSuccessful()
        );

    }

    @Test
    @DisplayName("ApplyInformationId를 검색하는 테스트 - 성공")
    public void findApplyIdSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();
        UUID missionId = UUID.randomUUID();

        UUID applyInformationId = UUID.randomUUID();
        String jsonResponse = gson.toJson(applyInformationId);

        when(applyInformationService.findApplyInformationId(any(UUID.class), any(UUID.class)))
                .thenReturn(applyInformationId);
        //when
        //then
        mvc.perform(
                get("/testers/" + testerId + "/missions/" + missionId)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(jsonResponse)
        );
    }


    @Test
    @DisplayName("MissioList를 남은 기간 순으로 조회하는 API")
    public void findTestListByDeadlineSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();

        SimpleMissionListResponse response = mock(SimpleMissionListResponse.class);
        when(missionService.applyMissionListByDeadline(testerId))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                get("/testers/" + testerId + "/missions/deadline")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("MissioList를 인기순으로 조회하는 API")
    public void findTestListByPopularSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();

        SimpleMissionListResponse response = mock(SimpleMissionListResponse.class);
        when(missionService.applyMissionListByPopular(testerId))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                get("/testers/" + testerId + "/missions/popular")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("MissioList를 생성순으로 조회하는 API")
    public void findTestListByCreatedSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();

        SimpleMissionListResponse response = mock(SimpleMissionListResponse.class);
        when(missionService.applyMissionListByCreated(testerId))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                get("/testers/" + testerId + "/missions/created")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("MissioList를 기본으로 조회하는 API")
    public void findTestListByNormalSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();

        SimpleMissionListResponse response = mock(SimpleMissionListResponse.class);
        when(missionService.applyMissionListByCreated(testerId))
                .thenReturn(response);

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
        UUID applyInformationId = UUID.randomUUID();
        CreateMakerReviewRequest request = mock(CreateMakerReviewRequest.class);
        String jsonRequest = gson.toJson(request);
        CreateMakerReviewResponse response = mock(CreateMakerReviewResponse.class);
        String jsonResponse = gson.toJson(response);

        when(makerReviewService.createMakerReview(any(UUID.class), any(CreateMakerReviewRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                post("/testers/apply/" + applyInformationId + "/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );
    }


    @Test
    @DisplayName("교환하기 위한 화면 정보 조회 테스트 - 성공")
    public void exchangeViewSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();

        ExchangeInformationResponse response = mock(ExchangeInformationResponse.class);
        when(testerService.exchangeView(any(UUID.class)))
                .thenReturn(response);
        //when
        //then
        mvc.perform(
                get("/testers/" + testerId + "/exchange")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("계좌 정보 업데이트를 위한 테스트 - 성공")
    public void updateAccountSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();
        AccountRequest request = mock(AccountRequest.class);
        String jsonRequest = gson.toJson(request);
        AccountResponse response = mock(AccountResponse.class);
        when(testerService.updateAccount(any(UUID.class), any(AccountRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                patch("/testers/" + testerId + "/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    @DisplayName("Point를 Cash로 전환하는 Test - 성공")
    public void changePointToCashSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.randomUUID();
        ChangePointToCashRequest request = mock(ChangePointToCashRequest.class);
        String jsonRequest = gson.toJson(request);
        ChangePointToCashResponse response = mock(ChangePointToCashResponse.class);
        when(testerService.changePointToCash(any(UUID.class), any(ChangePointToCashRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                post("/testers/" + testerId + "/exchange/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

    }




}