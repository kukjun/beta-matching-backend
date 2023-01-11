package io.wisoft.testermatchingplatform.web.controller;

import com.google.gson.Gson;
import io.wisoft.testermatchingplatform.service.*;
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

@WebMvcTest(controllers = MakerController.class)
class MakerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MakerService makerService;
    @MockBean
    private MissionService missionService;
    @MockBean
    private ApplyInformationService applyInformationService;
    @MockBean
    private TesterReviewService testerReviewService;

    private final Gson gson = new Gson();

    // Test 생성, 수정에 대해서는 MultipartForm의 형태로 request를 만드는 법을 몰라서 Pass

    @Test
    @DisplayName("만든 missionList를 조회 테스트 - 성공")
    public void findMadeMissionList() throws Exception {
        //given
        UUID makerId = UUID.randomUUID();
        ApplyMissionListFromMakerResponse response = mock(ApplyMissionListFromMakerResponse.class);
        when(missionService.madeMissionListFromMaker(any(UUID.class)))
                .thenReturn(response);

        //when
        mvc.perform(
                get("/makers/" + makerId + "/missions")
        ).andExpect(
                status().isOk()
        );

        //then
    }


    @Test
    @DisplayName("mission을 신청한 인원들 조회 테스트 - 성공")
    public void findApplyTestSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        ApplyTesterListResponse response = mock(ApplyTesterListResponse.class);
        when(applyInformationService.findApplyTesterList(any(UUID.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                get("/makers/missions/" + missionId + "/apply")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("mission을 수행하는 인원들 조회 테스트 - 성공")
    public void findPerformTestersSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        PerformTesterListResponse response = mock(PerformTesterListResponse.class);
        when(applyInformationService.findPerformTesterList(any(UUID.class)))
                .thenReturn(response);
        //when
        //then
        mvc.perform(
                get("/makers/missions/" + missionId + "/perform")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("mission을 완료한 인원들 조회 테스트 - 성공")
    public void findCompleteTestersSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        TesterListOfClosedMissionResponse response = mock(TesterListOfClosedMissionResponse.class);
        when(applyInformationService.findTesterListOfClosedMission(any(UUID.class)))
                .thenReturn(response);
        //when
        //then
        mvc.perform(
                get("/makers/missions/" + missionId + "/perform/review")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("교환하기 위한 화면 정보 조회 테스트 - 성공")
    public void exchangeViewSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.randomUUID();

        ExchangeInformationResponse response = mock(ExchangeInformationResponse.class);
        when(makerService.exchangeView(any(UUID.class)))
                .thenReturn(response);
        //when
        //then
        mvc.perform(
                get("/makers/" + makerId + "/exchange")
        ).andExpect(
                status().isOk()
        );
    }


    @Test
    @DisplayName("계좌 정보 업데이트를 위한 테스트 - 성공")
    public void updateAccountSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.randomUUID();
        AccountRequest request = mock(AccountRequest.class);
        String jsonRequest = gson.toJson(request);
        AccountResponse response = mock(AccountResponse.class);
        when(makerService.updateAccount(any(UUID.class), any(AccountRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                patch("/makers/" + makerId + "/account")
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
        UUID makerId = UUID.randomUUID();
        ChangePointToCashRequest request = mock(ChangePointToCashRequest.class);
        String jsonRequest = gson.toJson(request);
        ChangePointToCashResponse response = mock(ChangePointToCashResponse.class);
        when(makerService.changePointToCash(any(UUID.class), any(ChangePointToCashRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                post("/makers/" + makerId + "/exchange/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

    }

    @Test
    @DisplayName("Cash를 Point로 전환하는 Test - 성공")
    public void changeCashToPointSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.randomUUID();
        ChangeCashToPointRequest request = mock(ChangeCashToPointRequest.class);
        String jsonRequest = gson.toJson(request);
        ChangeCashToPointResponse response = mock(ChangeCashToPointResponse.class);
        when(makerService.changeCashToPoint(any(UUID.class), any(ChangeCashToPointRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                post("/makers/" + makerId + "/exchange/cash")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("신청한 Tester, 수행으로 변경하는 Test - 성공")
    public void changeApplyToPerformSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        ChangeApplyToApproveRequest request = mock(ChangeApplyToApproveRequest.class);
        String jsonRequest = gson.toJson(request);
        ChangeApplyToApproveResponse response = mock(ChangeApplyToApproveResponse.class);
        when(applyInformationService.applyToApprove(any(UUID.class), any(ChangeApplyToApproveRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                post("/makers/missions/" + missionId + "/perform")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("수행한 Tester, 완료로 변경하는 Test - 성공")
    public void changePerformToCompleteSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.randomUUID();
        ChangePerformToCompleteRequest request = mock(ChangePerformToCompleteRequest.class);
        String jsonRequest = gson.toJson(request);
        ChangePerformToCompleteResponse response = mock(ChangePerformToCompleteResponse.class);
        when(applyInformationService.performToComplete(any(UUID.class), any(ChangePerformToCompleteRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                post("/makers/missions/" + missionId + "/complete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("완료 Mission을 수행한 Tester 리뷰 작성 테스트 - 성공")
    public void createReviewSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.randomUUID();
        CreateTesterReviewListRequest request = mock(CreateTesterReviewListRequest.class);
        String jsonRequest = gson.toJson(request);
        CreateTesterReviewListResponse response = mock(CreateTesterReviewListResponse.class);
        when(testerReviewService.createTesterReview(any(UUID.class), any(CreateTesterReviewListRequest.class)))
                .thenReturn(response);

        //when
        //then
        mvc.perform(
                post("/makers/" + makerId + "/missions/perform/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isOk()
        );
    }


}