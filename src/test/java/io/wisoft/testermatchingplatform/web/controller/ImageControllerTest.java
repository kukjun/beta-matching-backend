//package io.wisoft.testermatchingplatform.web.controller;
//
//import io.wisoft.testermatchingplatform.handler.FileHandler;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.io.File;
//import java.nio.file.Files;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mockStatic;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ImageController.class)
//class ImageControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    @DisplayName("Controller를 이용해서 File이 정상적으로 전달되는지 테스트 - 성공")
//    public void imageSuccessTest() throws Exception {
//        //given
//        String testFilePath = System.getProperty("user.dir") + "/src/test/resources/";
//        String fieldName = "CreateMissionImage.png";
//        File file = new File(testFilePath + fieldName);
//        byte[] byteFile = Files.readAllBytes(file.toPath());
//        String contentType = "image/png";
//        MediaType mediaType = MediaType.valueOf(contentType);
//        System.out.println("mediaType = " + mediaType);
//
//        //when
//        //then
//        try (MockedStatic<FileHandler> fileHandlerMockedStatic = mockStatic(FileHandler.class);
//             MockedStatic<MediaType> mediaTypeMockedStatic = mockStatic(MediaType.class)
//        ) {
//            fileHandlerMockedStatic.when(() -> FileHandler.getMissionRepresentationFileData(any(String.class)))
//                    .thenReturn(byteFile);
//            fileHandlerMockedStatic.when(() -> FileHandler.getImageContentType(any(String.class)))
//                    .thenReturn(contentType);
//            mediaTypeMockedStatic.when(() -> MediaType.valueOf(contentType))
//                    .thenReturn(mediaType);
//            mvc.perform(
//                    get("/api/image")
//                            .param("mission_representation_image", fieldName)
//            ).andExpect(
//                    status().isOk()
//            );
//
//        }
//
//    }
//
//}