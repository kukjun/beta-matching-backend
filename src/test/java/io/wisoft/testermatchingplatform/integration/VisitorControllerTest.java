package io.wisoft.testermatchingplatform.integration;

import com.google.gson.Gson;
import io.wisoft.testermatchingplatform.service.MakerService;
import io.wisoft.testermatchingplatform.service.TesterService;
import io.wisoft.testermatchingplatform.web.controller.VisitorController;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterRequest;
import io.wisoft.testermatchingplatform.web.dto.request.MakerLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.response.CreateMakerResponse;
import io.wisoft.testermatchingplatform.web.dto.response.CreateTesterResponse;
import io.wisoft.testermatchingplatform.web.dto.response.MakerLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.TesterLoginResponse;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class VisitorControllerTest {

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
    @DisplayName("통합 테스트: tester 회원가입 테스트 - 성공")
    public void testerRegisterSuccessTest() throws Exception {
        //given
        CreateTesterRequest request = CreateTesterRequest.newInstance(
                "newTester@naver.com", "password", "newTester", "010-5164-1361", "introMessage"
        );
        String jsonRequest = gson.toJson(request);


        //when
        //then
        mvc.perform(post("/visitor/testers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("통합 테스트: tester 로그인 테스트 - 성공")
    public void testerLoginSuccessTest() throws Exception {
        //given
        TesterLoginRequest request = TesterLoginRequest.newInstance(
                "sena@naver.com", "1234"
        );
        String jsonRequest = gson.toJson(request);

        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b2");
        TesterLoginResponse response = TesterLoginResponse.newInstance("token", testerId, "임세나");
        String jsonResponse = gson.toJson(response);


        //when
        mvc.perform(post("/visitor/testers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse));

        //then
    }

    @Test
    @DisplayName("통합 테스트: maker 회원가입 테스트 - 성공")
    public void makerRegisterSuccessTest() throws Exception {
        //given
        CreateMakerRequest request = CreateMakerRequest.newInstance(
                "newMaker@naver.com", "password", "newMaker", "010-5164-1361", "네이보"
        );
        String jsonRequest = gson.toJson(request);
        UUID testerId = UUID.randomUUID();

        //when
        //then
        mvc.perform(post("/visitor/makers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("통합 테스트: maker 로그인 테스트 - 성공")
    public void makerLoginSuccessTest() throws Exception {
        //given
        MakerLoginRequest request = MakerLoginRequest.newInstance(
                "kukjun@naver.com", "1234"
        );
        String jsonRequest = gson.toJson(request);
        UUID makerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5a0");
        MakerLoginResponse response = MakerLoginResponse.newInstance(
                "token", makerId, "이국준"
        );
        String jsonResponse = gson.toJson(response);


        //when
        //then
        mvc.perform(post("/visitor/makers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse));
    }

}