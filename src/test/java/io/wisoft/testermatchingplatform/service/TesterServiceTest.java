package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.handler.exception.LoginException;
import io.wisoft.testermatchingplatform.repository.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.request.AccountRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePointToCashRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterRequest;
import io.wisoft.testermatchingplatform.web.dto.request.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.response.AccountResponse;
import io.wisoft.testermatchingplatform.web.dto.response.ChangePointToCashResponse;
import io.wisoft.testermatchingplatform.web.dto.response.CreateTesterResponse;
import io.wisoft.testermatchingplatform.web.dto.response.TesterLoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TesterServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private TesterRepository testerRepository;

    @Autowired
    private TesterService testerService;

    @Test
    @DisplayName("테스터 생성 테스트 - 성공")
    public void saveTesterSuccessTest() throws Exception {
        //given
        String email = "testTester@naver.com";
        String password = "1234";
        String nickname = "testman";
        String phoneNumber = "010-1234-1234";
        String introMessage = "안녕하세용 테스터입니다";
        CreateTesterRequest request = CreateTesterRequest.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        );

        //when
        CreateTesterResponse response = testerService.createTester(request);

        //then
        Tester storeTester = testerRepository.findById(response.getId());
        assertEquals(email, storeTester.getEmail());

    }


    @Test
    @DisplayName("로그인 테스트 - 성공")
    public void LoginSuccessTest() throws Exception {
        //given
        String email = "testTester@naver.com";
        String password = "1234";
        String nickname = "testman";
        String phoneNumber = "010-1234-1234";
        String introMessage = "안녕하세용 테스터입니다";
        UUID saveId = testerRepository.save(Tester.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        ));
        TesterLoginRequest request = TesterLoginRequest.newInstance(
                email,
                password
        );

        //when
        TesterLoginResponse response = testerService.login(request);

        //then
        assertEquals(saveId, response.getId());
        assertEquals(nickname, response.getNickname());
    }

    @Test
    @DisplayName("로그인 테스트 - 실패(해당하는 회원이 없음)")
    public void LoginNotEqualsEmailTest() {
        //given
        String email = "testTester@naver.com";
        String password = "1234";
        String nickname = "testman";
        String phoneNumber = "010-1234-1234";
        String introMessage = "안녕하세용 테스터입니다";
        UUID saveId = testerRepository.save(Tester.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        ));

        String notEqualsEmail = "testMaker@naver.com";
        TesterLoginRequest request = TesterLoginRequest.newInstance(
                notEqualsEmail,
                password
        );
        //when
        //then
        assertThrows(
                LoginException.class,
                () -> testerService.login(request)
        );
    }

    @Test
    @DisplayName("로그인 테스트 - 실패(비밀번호가 다름)")
    public void LoginNotEqualsPasswordTest() {
        //given
        String email = "testTester@naver.com";
        String password = "1234";
        String nickname = "testman";
        String phoneNumber = "010-1234-1234";
        String introMessage = "안녕하세용 테스터입니다";
        UUID saveId = testerRepository.save(Tester.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        ));

        String notEqualsPassword = "12345";
        TesterLoginRequest request = TesterLoginRequest.newInstance(
                email,
                notEqualsPassword
        );
        //when
        //then
        assertThrows(
                LoginException.class,
                () -> testerService.login(request)
        );
    }

    @Test
    @DisplayName("계좌 수정 테스트 - 성공")
    public void UpdateAccountSuccessTest() throws Exception {
        //given
        String email = "testTester@naver.com";
        String password = "1234";
        String nickname = "testman";
        String phoneNumber = "010-1234-1234";
        String introMessage = "안녕하세용 테스터입니다";
        UUID saveId = testerRepository.save(Tester.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        ));

        String account = "1234-12441-12445";
        AccountRequest request = AccountRequest.newInstance(
                account
        );
        //when
        AccountResponse response = testerService.updateAccount(saveId, request);

        //then
        assertEquals(account, response.getAccount());
    }

    @Test
    @DisplayName("포인트 현금으로 전환 성공 테스트")
    public void changePointToCashSuccessTest() throws Exception {
        //given
        String email = "testTester@naver.com";
        String password = "1234";
        String nickname = "testman";
        String phoneNumber = "010-1234-1234";
        String introMessage = "안녕하세용 테스터입니다";
        UUID saveId = testerRepository.save(Tester.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        ));
        Tester tester = testerRepository.findById(saveId);
        long point = 10000L;
        tester.rewardPoint(point);

        long changePoint = 1000L;
        long remainPoint = point - changePoint;
        ChangePointToCashRequest request = ChangePointToCashRequest.newInstance(changePoint);
        long expectedCash = changePoint * 19 / 20;

        //when
        ChangePointToCashResponse response = testerService.changePointToCash(saveId, request);

        //then
        assertEquals(expectedCash, response.getCash());
        assertEquals(remainPoint, tester.getPoint());
    }




}