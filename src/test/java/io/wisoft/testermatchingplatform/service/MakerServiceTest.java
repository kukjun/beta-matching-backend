package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.web.dto.request.*;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
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
class MakerServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private MakerRepository makerRepository;
    @Autowired
    private MakerService makerService;

    private Maker normalMaker;
    @BeforeEach
    public void prepareTest() {
        String email = "prepareTestMaker@naver.com";
        String password = "12345";
        String nickname = "maker입니당ㅎㅎ";
        String phoneNumber = "010-2341-1422";
        String company = "Kakao";
        normalMaker = Maker.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                company
        );
        makerRepository.save(normalMaker);
    }

    @Test
    @DisplayName("Maker 생성 테스트 - 성공")
    public void CreateMakerSuccessTest() throws Exception {
        //given
        String email = "TestMaker@naver.com";
        String password = "1234";
        String nickname = "maker입니당";
        String phoneNumber = "010-2341-1422";
        String company = "Naver";




        CreateMakerRequest request = CreateMakerRequest.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                company
        );

        //when
        CreateMakerResponse response = makerService.createMaker(request);

        //then
        Maker storeMaker = makerRepository.findById(response.getId());
        assertEquals(email, storeMaker.getEmail());
        assertEquals(password, storeMaker.getPassword());
    }

    @Test
    @DisplayName("계좌 수정 테스트 - 성공")
    public void UpdateAccountSuccessTest() throws Exception {
        //given
        String account = "1234-1241-14142466";
        AccountRequest request = AccountRequest.newInstance(account);

        //when
        AccountResponse response = makerService.updateAccount(normalMaker.getId(), request);

        //then
        assertEquals(account, response.getAccount());
    }

    @Test
    @DisplayName("포인트 현금 전환 테스트 - 성공")
    public void changePointToCashSuccessTest() throws Exception {
        //given

        long cash = 10000L;
        normalMaker.cashToPoint(cash);

        long requirePoint = 1000L;
        long expectedCash = requirePoint * 19 / 20;
        ChangePointToCashRequest request = ChangePointToCashRequest.newInstance(requirePoint);

        //when
        ChangePointToCashResponse response = makerService.changePointToCash(normalMaker.getId(), request);

        //then
        assertEquals(expectedCash, response.getCash());
    }

    @Test
    @DisplayName("현금 포인트 전환 테스트 - 성공")
    public void ChangeCashToPointSuccessTest() throws Exception {
        //given
        long cash = 10000L;
        long expectedPoint = cash;

        ChangeCashToPointRequest request = ChangeCashToPointRequest.newInstance(cash);

        //when
        ChangeCashToPointResponse response = makerService.changeCashToPoint(normalMaker.getId(), request);

        //then
        assertEquals(expectedPoint, response.getPoint());
    }

    @Test
    @DisplayName("Maker 로그인 테스트 - 성공")
    public void makerLoginTest() throws Exception {
        //given
        String email = "prepareTestMaker@naver.com";
        String password = "12345";
        MakerLoginRequest request = MakerLoginRequest.newInstance(email, password);

        //when
        MakerLoginResponse response = makerService.login(request);

        //then
        assertEquals(normalMaker.getId(), response.getId());
        assertEquals(normalMaker.getNickname(), response.getNickname());
    }

    @Test
    @DisplayName("교환화면 정보 호출 테스트 - 성공")
    public void exchangeViewSuccessTest() throws Exception {
        //given
        String account = "1234-14512-1251256";
        long cash = 10000L;
        normalMaker.changeAccount(account);
        normalMaker.cashToPoint(cash);

        //when
        ExchangeInformationResponse response = makerService.exchangeView(normalMaker.getId());

        //then
        assertEquals(account, response.getAccount());
        assertEquals(cash, response.getPoint());
    }




}