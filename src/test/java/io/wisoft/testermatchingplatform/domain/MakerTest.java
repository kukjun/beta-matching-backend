package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.domain.InsufficientPointException;
import io.wisoft.testermatchingplatform.handler.exception.domain.MissMatchPasswordException;
import io.wisoft.testermatchingplatform.handler.exception.domain.NotNaturalNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class MakerTest {

    private Maker normalMaker;

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


    @Test
    @DisplayName("생성 시 기본 값 테스트")
    public void makerCreateInitValueSuccessTest() {
        //given
        long expectPoint = 0L;
        String expectAccount = "";
        // when

        // then
        assertEquals(expectPoint, normalMaker.getPoint());
        assertEquals(expectAccount, normalMaker.getAccount());
    }

    @Test
    @DisplayName("비밀번호 유효여부 확인 테스트 - 성공")
    public void loginSuccessTest() {
        // given
        String password = "abcd1234";

        // when, then
        assertDoesNotThrow(() -> normalMaker.verifyPassword(password));

    }

    @Test
    @DisplayName("비밀번호 유효여부 확인 테스트 - 실패")
    public void loginFailTest() {
        // given
        String password = "abcd123";

        // when, then
        assertThrows(MissMatchPasswordException.class, () -> normalMaker.verifyPassword(password));

    }

    @Test
    @DisplayName("현금 포인트 전환 테스트 - 실패")
    public void withdrawCashFailTest() {
        // given
        long cash = -10;

        // when, then
        assertThrows(NotNaturalNumberException.class, () -> normalMaker.cashToPoint(cash));
    }

    @Test
    @DisplayName("현금 포인트 전환 테스트 - 성공")
    public void withdrawCashSuccessTest() {
        // given
        long cash = 1000L;
        long expectedPoint = cash;

        // when, then
        assertDoesNotThrow(() -> normalMaker.cashToPoint(cash));
        assertEquals(expectedPoint, normalMaker.getPoint());
    }

    @Test
    @DisplayName("포인트 현금 전환 테스트 - 실패")
    public void depositPointFailTest() {
        // given
        long cash = 1000L;
        long withdrawPoint = 10000L;
        normalMaker.cashToPoint(cash);

        // when, then
        assertThrows(InsufficientPointException.class, () -> normalMaker.pointToCash(withdrawPoint));
    }

    @Test
    @DisplayName("포인트 현금 전환 테스트 - 성공")
    public void depositPointSuccessTest() {
        // given
        long cash = 10000L;
        long minusPoint = 1000L;
        normalMaker.cashToPoint(cash);

        long remainCash = normalMaker.getPoint() - minusPoint;

        // when, then
        assertDoesNotThrow(() -> normalMaker.pointToCash(minusPoint));
        assertEquals(remainCash, normalMaker.getPoint());
    }

    @Test
    @DisplayName("포인트 사용 성공 테스트")
    public void usePointSuccessTest() {
        // given
        long cash = 10000L;
        normalMaker.cashToPoint(cash);
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
        normalMaker.cashToPoint(cash);
        long minusPoint = 10000L;

        long remainPoint = normalMaker.getPoint() - minusPoint;

        // when
        // then
        assertThrows(InsufficientPointException.class, () -> normalMaker.usePoint(minusPoint));
    }
}


