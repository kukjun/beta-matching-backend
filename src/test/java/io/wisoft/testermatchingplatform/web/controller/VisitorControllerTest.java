package io.wisoft.testermatchingplatform.web.controller;

import com.google.gson.Gson;
import io.wisoft.testermatchingplatform.service.MakerService;
import io.wisoft.testermatchingplatform.service.TesterService;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterRequest;
import io.wisoft.testermatchingplatform.web.dto.request.MakerLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.response.CreateMakerResponse;
import io.wisoft.testermatchingplatform.web.dto.response.CreateTesterResponse;
import io.wisoft.testermatchingplatform.web.dto.response.MakerLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.TesterLoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VisitorController.class)
class VisitorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TesterService testerService;

    @MockBean
    private MakerService makerService;

    private final Gson gson = new Gson();

    @Test
    @DisplayName("tester 회원가입 테스트 - 성공")
    public void testerRegisterSuccessTest() throws Exception {
        //given
        CreateTesterRequest request = CreateTesterRequest.newInstance(
                "email", "password", "nickname", "phoneNumber", "introMessage"
        );
        String jsonRequest = gson.toJson(request);
        UUID testerId = UUID.randomUUID();
        CreateTesterResponse response = CreateTesterResponse.newInstance(testerId);
        String jsonResponse = gson.toJson(response);

        when(testerService.createTester(request)).thenReturn(response);

        //when
        //then
        mvc.perform(post("/visitor/testers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("tester 로그인 테스트 - 성공")
    public void testerLoginSuccessTest() throws Exception {
        //given
        TesterLoginRequest request = TesterLoginRequest.newInstance(
                "email", "password"
        );
        String jsonRequest = gson.toJson(request);

        UUID testerId = UUID.randomUUID();
        TesterLoginResponse response = TesterLoginResponse.newInstance(testerId, "nickname");
        String jsonResponse = gson.toJson(response);

        when(testerService.login(any(TesterLoginRequest.class))).thenReturn(response);

        //when
        mvc.perform(post("/visitor/testers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse))
                .andExpect(
                        header().exists("AUTHORIZATION")
                );

        //then
    }

    @Test
    @DisplayName("maker 회원가입 테스트 - 성공")
    public void makerRegisterSuccessTest() throws Exception {
        //given
        CreateMakerRequest request = CreateMakerRequest.newInstance(
                "email", "password", "nickname", "phoneNumber", "company"
        );
        String jsonRequest = gson.toJson(request);
        UUID testerId = UUID.randomUUID();
        CreateMakerResponse response = CreateMakerResponse.newInstance(testerId);
        String jsonResponse = gson.toJson(response);

        when(makerService.createMaker(request)).thenReturn(response);

        //when
        //then
        mvc.perform(post("/visitor/makers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("maker 로그인 테스트 - 성공")
    public void makerLoginSuccessTest() throws Exception {
        //given
        MakerLoginRequest request = MakerLoginRequest.newInstance(
                "email", "password"
        );
        String jsonRequest = gson.toJson(request);

        MakerLoginResponse response = mock(MakerLoginResponse.class);
        String jsonResponse = gson.toJson(response);

        when(makerService.login(any(MakerLoginRequest.class))).thenReturn(response);

        //when
        mvc.perform(post("/visitor/makers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(
                        header().exists("AUTHORIZATION")
                );

        //then
    }

}