package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.CashException;
import io.wisoft.testermatchingplatform.handler.exception.LoginException;
import io.wisoft.testermatchingplatform.handler.exception.PointException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MakerTest {

    private Maker normalMaker;

    private Maker weirdMaker;

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    void normalCreateMaker() {
        String email = "abcd@naver.com";
        String password = "abcd1234";
        String nickname = "에이비씨";
        String phone = "010-1234-5678";
        String company = "카카오";

        normalMaker = Maker.newInstance(
                email,
                password,
                nickname,
                phone,
                company
        );
    }

    @BeforeEach
    void weirdCreateMaker() {
        String email = "abcdef";
        String password = "abcd1234";
        String nickname = "에이비씨";
        String phone = "010-1234-5678";
        String company = "카카오";

        weirdMaker = Maker.newInstance(
                email,
                password,
                nickname,
                phone,
                company
        );
    }


    @Test
    @DisplayName("생성 시 기본 값 테스트")
    public void makerCreateInitValueSuccessTest() {
        //given

        // when

        // then
        assertEquals(0L, normalMaker.getPoint());
        assertEquals("", normalMaker.getAccount());
    }

    @Test
    @DisplayName("생성 시, Email Validation 오류 테스트")
    public void makerEmailValidationFailTest() {
        // given

        // when

        // then
        Set<ConstraintViolation<Maker>> violations = validator.validate(weirdMaker);

        ArrayList<ConstraintViolation<Maker>> constraintViolations = new ArrayList<>(violations);
        ConstraintViolation<Maker> makerConstraintViolation = constraintViolations.get(0);
        assertEquals("email", makerConstraintViolation.getPropertyPath().toString());

    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcd1234";

        // when, then
        assertDoesNotThrow(() -> normalMaker.login(email, password));

    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcd123";

        // when, then
        assertThrows(LoginException.class, () -> normalMaker.login(email, password));

    }

    @Test
    @DisplayName("현금 포인트 전환 실패 테스트 - 비정상적인 현금")
    public void withdrawCashFailTest() {
        // given
        long cash = -10;

        // when, then
        assertThrows(CashException.class, () -> normalMaker.withdrawCash(cash));
    }

    @Test
    @DisplayName("현금 포인트 전환 성공 테스트")
    public void withdrawCashSuccessTest() {
        // given
        long cash = 1000L;

        // when, then
        assertDoesNotThrow(() -> normalMaker.withdrawCash(cash));
        assertEquals(cash, normalMaker.getPoint());
    }

    @Test
    @DisplayName("포인트 현금 전환 실패 테스트")
    public void depositPointFailTest() {
        // given
        long cash = 1000L;
        long minusPoint = 10000L;
        normalMaker.withdrawCash(cash);

        // when, then
        assertThrows(PointException.class, () -> normalMaker.depositPoint(minusPoint));
    }

    @Test
    @DisplayName("포인트 현금 전환 성공 테스트")
    public void depositPointSuccessTest() {
        // given
        long cash = 10000L;
        long minusPoint = 1000L;
        normalMaker.withdrawCash(cash);

        long remainCash = normalMaker.getPoint() - minusPoint;

        // when, then
        assertDoesNotThrow(() -> normalMaker.depositPoint(minusPoint));
        assertEquals(remainCash, normalMaker.getPoint());

    }

    @Test
    @DisplayName("포인트 사용 성공 테스트")
    public void usePointSuccessTest() {
        // given
        long cash = 10000L;
        normalMaker.withdrawCash(cash);
        long minusPoint = 1000L;

        long remainPoint = normalMaker.getPoint() - minusPoint;

        // when
        // then
        assertDoesNotThrow(() -> normalMaker.usePoint(minusPoint));
        assertEquals(remainPoint, normalMaker.getPoint());
    }

    @Test
    @DisplayName("포인트 사용 실패 테스트")
    public void usePointFailTest() {
        // given
        long cash = 1000L;
        normalMaker.withdrawCash(cash);
        long minusPoint = 10000L;

        long remainPoint = normalMaker.getPoint() - minusPoint;

        // when
        // then
        assertThrows(PointException.class, () -> normalMaker.usePoint(minusPoint));
    }
}


