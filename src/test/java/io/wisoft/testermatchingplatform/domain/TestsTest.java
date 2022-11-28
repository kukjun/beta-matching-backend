package io.wisoft.testermatchingplatform.domain;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;

class TestsTest {

    private static Validator validator;

    private Tests normalTest;

    private Tests wriedTest;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @BeforeEach
    public void createNormalTest() {
        String email = "abcd@naver.com";
        String password = "abcd1234";
        String nickname = "에이비씨";
        String phone = "010-1234-5678";
        String company = "카카오";

        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        String imageURL = "ImageURLCheck";
        long point = 100L;
        int limitApply = 100;
        Maker maker = Maker.newInstance(
                email,
                password,
                nickname,
                phone,
                company
        );
        long cash = 100000L;
        maker.withdrawCash(cash);
        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(5L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(20L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(30L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(50L);

        normalTest = Tests.newInstance(
                title,
                content,
                imageURL,
                point,
                limitApply,
                maker,
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
    }

    @Test
    @DisplayName("테스트 생성 성공 테스트")
    public void createTestSuccessTest() {
        // given
        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        long cash = 100000L;
        long point = 100L;
        int limitApply = 100;
        long remainPoint = cash - point * limitApply;
        // when
        // then
        Assertions.assertEquals(title, normalTest.getTitle());
        Assertions.assertEquals(content, normalTest.getContent());
        Assertions.assertEquals(remainPoint, normalTest.getMaker().getPoint());
    }

//    @Test
//    @DisplayName("테스트 생성 실패 테스트")
//    public void createTestFailTest() {
//        // given
//        String email = "abcd@naver.com";
//        String password = "abcd1234";
//        String nickname = "에이비씨";
//        String phone = "010-1234-5678";
//        String company = "카카오";
//
//        String title = "보통 테스트입니다.";
//        String content = "보통 테스트에 대한 내용입니다!";
//        String imageURL = "ImageURLCheck";
//        long point = 100L;
//        int limitApply = 100;
//        Maker maker = Maker.newInstance(
//                email,
//                password,
//                nickname,
//                phone,
//                company
//        );
//        long cash = 100000L;
//        maker.withdrawCash(cash);
//        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(5L);
//        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(20L);
//        LocalDate durationTimeStart = LocalDate.now().plusDays(30L);
//        LocalDate durationTimeEnd = LocalDate.now().plusDays(50L);
//
//    }

}
