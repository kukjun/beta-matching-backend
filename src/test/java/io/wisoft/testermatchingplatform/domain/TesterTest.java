package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.LoginException;
import io.wisoft.testermatchingplatform.handler.exception.PointException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TesterTest {

    private Tester normalTester;
    private Tester wriedTester;

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    public void createNormalTester() {
        String email = "abcd@naver.com";
        String password = "abcdef12345";
        String nickname = "kkukjun";
        String phone = "010-2243-1252";
        String introduce = "안녕하세요 저는 이국준이예요.";

        normalTester = Tester.newInstance(
                email,
                password,
                nickname,
                phone,
                introduce
        );
    }

    @BeforeEach
    public void createWriedTester() {
        String email = "abcdaver.com";
        String password = "abcdef12345";
        String nickname = "kkukjun";
        String phone = "010-2243-1252";
        String introduce = "안녕하세요 저는 이국준이예요.";

        wriedTester = Tester.newInstance(
                email,
                password,
                nickname,
                phone,
                introduce
        );
    }

    @Test
    public void createSuccessTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcdef12345";

        // when, then
        assertEquals(email, normalTester.getEmail());
        assertEquals(password, normalTester.getPassword());

    }

    @Test
    public void testerCreateValidationTest() {
        // given

        // when, then
        Set<ConstraintViolation<Tester>> violations = validator.validate(wriedTester);
        ArrayList<ConstraintViolation<Tester>> constraintViolations = new ArrayList<>(violations);
        ConstraintViolation<Tester> testerConstraintViolation = constraintViolations.get(0);
        assertEquals("email", testerConstraintViolation.getPropertyPath().toString());
    }

    @Test
    public void loginSuccessTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcdef12345";

        // when, then
        assertDoesNotThrow(() -> normalTester.login(email, password));
    }

    @Test
    public void loginFailTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcd12345";

        // when, then
        assertThrows(LoginException.class, () -> normalTester.login(email, password));
    }

    @Test
    public void rewardPointSuccessTest() {
        // given
        long point = 1000L;

        // when, then
        assertDoesNotThrow(()->normalTester.rewardPoint(point));
    }

    @Test
    public void rewardPointFailTest() {
        // given
        long point = -120L;

        // when, then
        assertThrows(PointException.class, () -> normalTester.rewardPoint(point));
    }

    @Test
    public void depositPointSuccessTest() {
        //given
        long rewardPoint = 10000L;
        long depositPoint = 5000L;
        normalTester.rewardPoint(rewardPoint);

        // when, then
        assertDoesNotThrow(() -> normalTester.depositPoint(depositPoint));
    }

    @Test
    public void depositPointFailTest() {
        //given
        long rewardPoint = 1000L;
        long depositPoint = 5000L;
        normalTester.rewardPoint(rewardPoint);

        // when, then
        assertThrows(PointException.class, () -> normalTester.depositPoint(depositPoint));
    }




}