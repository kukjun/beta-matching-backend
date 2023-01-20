//package io.wisoft.testermatchingplatform.integration;
//
//import io.wisoft.testermatchingplatform.handler.FileHandler;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import java.io.File;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
//class ImageControllerTest {
//    private MockMvc mvc;
//
//    @Autowired
//    private WebApplicationContext ctx;
//
//    @BeforeEach
//    public void prepareTest() {
//        mvc = MockMvcBuilders.webAppContextSetup(ctx)
//                .alwaysDo(print())
//                .addFilter(new CharacterEncodingFilter("UTF-8", true))
//                .build();
//    }
//
//
//    // 해당테스트 사용 시, 실제 이미지파일을 넣어주는 곳에 테스트용 이미지를 넣어놔야함. 수정 필요.
//    @Test
//    @DisplayName("통합 테스트: Controller를 이용해서 File이 정상적으로 전달되는지 테스트 - 성공")
//    public void imageSuccessTest() throws Exception {
//        //given
//        String testFilePath = System.getProperty("user.dir") + "/src/test/resources/";
//        String fieldName = "CreateMissionImage.png";
//        File file = new File(testFilePath + fieldName);
//        String contentType = "image/png";
//        MediaType mediaType = MediaType.valueOf(contentType);
//        System.out.println("mediaType = " + mediaType);
//
//        //when
//        //then
//
//        mvc.perform(
//                get("/api/image/" + fieldName)
//        ).andExpect(
//                status().isOk()
//        );
//
//    }
//
//}