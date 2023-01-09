package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.web.dto.request.*;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MakerServiceTest {

    @Mock
    private MakerRepository makerRepository;

    private MakerService makerService;

    @BeforeEach
    public void prepareTest() {
        makerService = new MakerService(makerRepository);
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
        UUID expectedId = UUID.randomUUID();
        Maker mockMaker = mock(Maker.class);
        when(mockMaker.getId()).thenReturn(expectedId);
        when(makerRepository.save(any(Maker.class))).thenReturn(mockMaker);


        //when
        CreateMakerResponse response = makerService.createMaker(request);

        //then
        assertEquals(expectedId, response.getId());

    }

    @Test
    @DisplayName("계좌 수정 테스트 - 성공")
    public void UpdateAccountSuccessTest() throws Exception {
        //given
        String expectedAccount = "1234-1241-14142466";
        AccountRequest request = AccountRequest.newInstance(expectedAccount);

        UUID id = UUID.randomUUID();

        Maker mockMaker = mock(Maker.class);
        when(mockMaker.changeAccount(expectedAccount)).thenReturn(expectedAccount);
        when(makerRepository.findById(id)).thenReturn(Optional.of(mockMaker));
        //when
        AccountResponse response = makerService.updateAccount(id, request);

        //then
        assertEquals(expectedAccount, response.getAccount());
    }

    @Test
    @DisplayName("포인트 현금 전환 테스트 - 성공")
    public void changePointToCashSuccessTest() throws Exception {
        //given
        long requirePoint = 1000L;
        long expectedCash = requirePoint * 19 / 20;
        ChangePointToCashRequest request = ChangePointToCashRequest.newInstance(requirePoint);

        UUID makerId = UUID.randomUUID();
        Maker mockMaker = mock(Maker.class);
        when(mockMaker.pointToCash(request.getPoint())).thenReturn(expectedCash);
        when(makerRepository.findById(makerId)).thenReturn(Optional.of(mockMaker));
        //when
        ChangePointToCashResponse response = makerService.changePointToCash(makerId, request);

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


        UUID makerId = UUID.randomUUID();
        Maker mockMaker = mock(Maker.class);
        when(mockMaker.cashToPoint(request.getCash())).thenReturn(expectedPoint);
        when(makerRepository.findById(makerId)).thenReturn(Optional.of(mockMaker));

        //when
        ChangeCashToPointResponse response = makerService.changeCashToPoint(makerId, request);

        //then
        assertEquals(expectedPoint, response.getPoint());
    }

    @Test
    @DisplayName("Maker 로그인 테스트 - 성공")
    public void makerLoginTest() throws Exception {
        //given
        String email = "prepareTestMaker@naver.com";
        String password = "12345";
        String expectNickname = "Test man";
        MakerLoginRequest request = MakerLoginRequest.newInstance(email, password);
        UUID expectedId = UUID.randomUUID();

        Maker mockMaker = mock(Maker.class);
        when(mockMaker.getNickname()).thenReturn(expectNickname);
        when(mockMaker.getId()).thenReturn(expectedId);
        when(makerRepository.findByEmail(request.getEmail())).thenReturn(mockMaker);


        //when
        MakerLoginResponse response = makerService.login(request);

        //then
        assertEquals(expectedId, response.getId());
        assertEquals(expectNickname, response.getNickname());
    }

    @Test
    @DisplayName("교환화면 정보 호출 테스트 - 성공")
    public void exchangeViewSuccessTest() throws Exception {
        //given
        String account = "1234-14512-1251256";
        long point = 10000L;

        UUID makerId = UUID.randomUUID();
        Maker mockMaker = mock(Maker.class);
        when(mockMaker.getPoint()).thenReturn(point);
        when(mockMaker.getAccountNumber()).thenReturn(account);
        when(makerRepository.findById(makerId)).thenReturn(Optional.of(mockMaker));
        //when
        ExchangeInformationResponse response = makerService.exchangeView(makerId);

        //then
        assertEquals(account, response.getAccount());
        assertEquals(point, response.getPoint());
    }


}