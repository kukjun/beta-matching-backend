package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.repository.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.request.AccountRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePointToCashRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterRequest;
import io.wisoft.testermatchingplatform.web.dto.request.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.response.AccountResponse;
import io.wisoft.testermatchingplatform.web.dto.response.ChangePointToCashResponse;
import io.wisoft.testermatchingplatform.web.dto.response.CreateTesterResponse;
import io.wisoft.testermatchingplatform.web.dto.response.TesterLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TesterServiceTest {

    @Mock
    private TesterRepository testerRepository;

    private TesterService testerService;

    @BeforeEach
    public void prepareTest() {
        testerService = new TesterService(testerRepository);
    }
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
        UUID makerId = UUID.randomUUID();
        when(testerRepository.save(any(Tester.class))).thenReturn(makerId);

        //when
        CreateTesterResponse response = testerService.createTester(request);

        //then
        assertEquals(makerId, response.getId());

    }


    @Test
    @DisplayName("로그인 테스트 - 성공")
    public void LoginSuccessTest() throws Exception {
        //given
        String email = "testTester@naver.com";
        String password = "1234";
        TesterLoginRequest request = TesterLoginRequest.newInstance(
                email,
                password
        );
        String expectNickname = "TesterLoginTestSuccess";

        UUID makerId = UUID.randomUUID();
        Tester mockTester = mock(Tester.class);
        when(mockTester.getId()).thenReturn(makerId);
        when(mockTester.getNickname()).thenReturn(expectNickname);
        when(testerRepository.findByEmail(email)).thenReturn(mockTester);

        //when
        TesterLoginResponse response = testerService.login(request);

        //then
        assertEquals(makerId, response.getId());
        assertEquals(expectNickname, response.getNickname());
    }

//    @Test
//    @DisplayName("로그인 테스트 - 실패(해당하는 회원이 없음)")
//    public void LoginNotEqualsEmailTest() {
//        //given
//        String email = "testTester@naver.com";
//        String password = "1234";
//        String nickname = "testman";
//        String phoneNumber = "010-1234-1234";
//        String introMessage = "안녕하세용 테스터입니다";
//        UUID saveId = testerRepository.save(Tester.newInstance(
//                email,
//                password,
//                nickname,
//                phoneNumber,
//                introMessage
//        ));
//
//        String notEqualsEmail = "testMaker@naver.com";
//        TesterLoginRequest request = TesterLoginRequest.newInstance(
//                notEqualsEmail,
//                password
//        );
//        //when
//        //then
//        assertThrows(
//                LoginException.class,
//                () -> testerService.login(request)
//        );
//    }
//
//    @Test
//    @DisplayName("로그인 테스트 - 실패(비밀번호가 다름)")
//    public void LoginNotEqualsPasswordTest() {
//        //given
//        String email = "testTester@naver.com";
//        String password = "1234";
//        String nickname = "testman";
//        String phoneNumber = "010-1234-1234";
//        String introMessage = "안녕하세용 테스터입니다";
//        UUID saveId = testerRepository.save(Tester.newInstance(
//                email,
//                password,
//                nickname,
//                phoneNumber,
//                introMessage
//        ));
//
//        String notEqualsPassword = "12345";
//        TesterLoginRequest request = TesterLoginRequest.newInstance(
//                email,
//                notEqualsPassword
//        );
//        //when
//        //then
//        assertThrows(
//                LoginException.class,
//                () -> testerService.login(request)
//        );
//    }

    @Test
    @DisplayName("계좌 수정 테스트 - 성공")
    public void UpdateAccountSuccessTest() throws Exception {
        //given
        String account = "1234-12441-12445";
        AccountRequest request = AccountRequest.newInstance(
                account
        );
        UUID testerId = UUID.randomUUID();
        Tester mockTester = mock(Tester.class);
        when(mockTester.changeAccount(account)).thenReturn(account);
        when(testerRepository.findById(testerId)).thenReturn(mockTester);
        //when
        AccountResponse response = testerService.updateAccount(testerId, request);

        //then
        assertEquals(account, response.getAccount());
    }

    @Test
    @DisplayName("포인트 현금으로 전환 테스트 - 성공")
    public void changePointToCashSuccessTest() throws Exception {
        //given
        long changePoint = 1000L;
        ChangePointToCashRequest request = ChangePointToCashRequest.newInstance(changePoint);
        long expectedCash = changePoint * 19 / 20;

        UUID testerId = UUID.randomUUID();
        Tester mockTester = mock(Tester.class);
        when(mockTester.pointToCash(request.getPoint())).thenReturn(expectedCash);
        when(testerRepository.findById(testerId)).thenReturn(mockTester);

        //when
        ChangePointToCashResponse response = testerService.changePointToCash(testerId, request);

        //then
        assertEquals(expectedCash, response.getCash());
    }




}