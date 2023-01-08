package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.handler.exception.service.TesterNotFoundException;
import io.wisoft.testermatchingplatform.repository.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.request.AccountRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePointToCashRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterRequest;
import io.wisoft.testermatchingplatform.web.dto.request.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TesterService {

    private final TesterRepository testerRepository;
    @Transactional
    public CreateTesterResponse createTester(final CreateTesterRequest request) {
        Tester tester = request.toTester();
        Tester storedTester = testerRepository.save(tester);

        CreateTesterResponse response = CreateTesterResponse.fromTester(storedTester);
        return response;
    }

    @Transactional
    public AccountResponse updateAccount(final UUID testerId, final AccountRequest request) {
        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("id: " + testerId + " not found")
        );
        String account = tester.changeAccount(request.getAccount());

        AccountResponse response = AccountResponse.fromAccount(account);
        return response;
    }

    @Transactional
    public ChangePointToCashResponse changePointToCash(final UUID testerId, final ChangePointToCashRequest request) {
        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("id: " + testerId + " not found")
        );
        long cash = tester.pointToCash(request.getPoint());

        ChangePointToCashResponse response = ChangePointToCashResponse.newInstance(cash);
        return response;
    }

    public ExchangeInformationResponse exchangeView(final UUID testerId) {
        Tester tester = testerRepository.findById(testerId).orElseThrow(
                () -> new TesterNotFoundException("id: " + testerId + " not found")
        );
        ExchangeInformationResponse response = ExchangeInformationResponse.fromTester(
                tester
        );
        return response;
    }

    public TesterLoginResponse login(final TesterLoginRequest request) {
        Tester tester = testerRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new TesterNotFoundException("email: " + request.getEmail() + " not found")
        );
            tester.verifyPassword(request.getPassword());
            TesterLoginResponse response = TesterLoginResponse.fromTester(tester);
            return response;
    }



}
