package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.handler.exception.LoginException;
import io.wisoft.testermatchingplatform.repository.TesterRepository;
import io.wisoft.testermatchingplatform.web.dto.request.AccountRequest;
import io.wisoft.testermatchingplatform.web.dto.request.ChangePointToCashRequest;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterRequest;
import io.wisoft.testermatchingplatform.web.dto.request.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TesterService {

    private final TesterRepository testerRepository;

    @Transactional
    public CreateTesterResponse createTester(final CreateTesterRequest request) {
        Tester tester = request.toTester();
        testerRepository.save(tester);

        CreateTesterResponse response = CreateTesterResponse.fromTester(tester);
        return response;
    }

    public TesterLoginResponse findTesterInformation(final TesterLoginRequest request) {
        try {
            Tester tester = testerRepository.findByEmail(request.getEmail());
            tester.checkPassword(request.getPassword());
            TesterLoginResponse response = TesterLoginResponse.fromTester(tester);
            return response;
        } catch (NoResultException no) {
            throw new LoginException();
        } catch (NonUniqueResultException noUnique) {
            throw new RuntimeException();
        }

    }

    @Transactional
    public AccountResponse updateAccount(final UUID testerId, final AccountRequest request) {
        Tester tester = testerRepository.findById(testerId);
        tester.changeAccount(request.getAccount());

        AccountResponse response = AccountResponse.fromTester(tester);
        return response;
    }

    @Transactional
    public ChangePointToCashResponse changePointToCash(final UUID testerId, final ChangePointToCashRequest request) {
        Tester tester = testerRepository.findById(testerId);
        long cash = tester.pointToCash(request.getPoint());

        ChangePointToCashResponse response = ChangePointToCashResponse.newInstance(cash);
        return response;
    }

    public ExchangeInformationResponse exchangeView(final UUID testerId) {
        Tester tester = testerRepository.findById(testerId);
        ExchangeInformationResponse response = ExchangeInformationResponse.fromTester(
                tester.getId(),
                tester.getAccount()
        );
        return response;
    }



}
