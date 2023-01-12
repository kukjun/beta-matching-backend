package io.wisoft.testermatchingplatform.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.wisoft.testermatchingplatform.service.ApplyInformationService;
import io.wisoft.testermatchingplatform.web.controller.BasicController;
import io.wisoft.testermatchingplatform.web.dto.response.CountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BasicControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void prepareTest() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("통합테스트: counts 호출하는 Test")
    public void countsSuccessTest() throws Exception {
        //given
        long expectTesterCount = (long) 4;
        long expectMakerCount = (long) 1;
        long expectContinueTestCount = (long) 5;
        long expectCompleteTestCount = (long) 1;

        //when
        //then
        MvcResult result = mvc.perform(get("/counts"))
                .andExpect(status().isOk())
                .andReturn();


        String response = result.getResponse().getContentAsString();
        int integerTesterCount = JsonPath.parse(response).read("$['testerCount']");
        long testerCount = (long) integerTesterCount;
        int integerMakerCount = JsonPath.parse(response).read("$['makerCount']");
        long makerCount = (long) integerMakerCount;
        int integerContinueTestCount = JsonPath.parse(response).read("$['continueTestCount']");
        long continueTestCount = (long) integerContinueTestCount;
        int integerCompleteTestCount = JsonPath.parse(response).read("$['completeTestCount']");
        long completeTestCount = (long) integerCompleteTestCount;

        assertEquals(expectTesterCount, testerCount);
        assertEquals(expectMakerCount, makerCount);
        assertEquals(expectContinueTestCount, continueTestCount);
        assertEquals(expectCompleteTestCount, completeTestCount);

    }

}