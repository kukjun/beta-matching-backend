package io.wisoft.testermatchingplatform.web.controller;

import com.google.gson.Gson;
import io.wisoft.testermatchingplatform.service.ApplyInformationService;
import io.wisoft.testermatchingplatform.web.dto.response.CountResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.Mockito.when;

@WebMvcTest(controllers = BasicController.class)
class BasicControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ApplyInformationService applyInformationService;

    private final Gson gson = new Gson();

    @Test
    @DisplayName("counts 호출하는 Test")
    public void countsSuccessTest() throws Exception {
        //given
        CountResponse expectResponse = CountResponse.newInstance(
                10,
                10,
                10,
                10
        );
        String toJson = gson.toJson(expectResponse);
        when(applyInformationService.findCount()).thenReturn(expectResponse);

        //when
        mvc.perform(MockMvcRequestBuilders.get("/counts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(toJson));

        //then
    }

}