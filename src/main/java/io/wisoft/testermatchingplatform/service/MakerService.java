package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.handler.exception.service.MakerNotFoundException;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.web.dto.request.*;
import io.wisoft.testermatchingplatform.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MakerService {

    private final MakerRepository makerRepository;

    @Transactional
    public CreateMakerResponse createMaker(final CreateMakerRequest request) {
        Maker maker = request.toMaker();
        Maker storedMaker = makerRepository.save(maker);
        CreateMakerResponse response = CreateMakerResponse.fromMaker(storedMaker);
        return response;
    }

    @Transactional
    public AccountResponse updateAccount(final UUID makerId, final AccountRequest request) {
        Maker maker = makerRepository.findById(makerId).orElseThrow(
                () -> new MakerNotFoundException("id: " + makerId + " not found")
        );
        String account = maker.changeAccount(request.getAccount());

        AccountResponse response = AccountResponse.fromAccount(account);
        return response;
    }

    @Transactional
    public ChangePointToCashResponse changePointToCash(final UUID makerId, final ChangePointToCashRequest request) {
        Maker maker = makerRepository.findById(makerId).orElseThrow(
                () -> new MakerNotFoundException("id: " + makerId + " not found")
        );
        long cash = maker.pointToCash(request.getPoint());


        ChangePointToCashResponse response = ChangePointToCashResponse.newInstance(cash);
        return response;
    }

    @Transactional
    public ChangeCashToPointResponse changeCashToPoint(final UUID makerId, final ChangeCashToPointRequest request) {
        Maker maker = makerRepository.findById(makerId).orElseThrow(
                () -> new MakerNotFoundException("id: " + makerId + " not found")
        );
        long point = maker.cashToPoint(request.getCash());

        ChangeCashToPointResponse response = ChangeCashToPointResponse.newInstance(point);
        return response;
    }

    public MakerLoginResponse login(final MakerLoginRequest request) {
            Maker maker = makerRepository.findByEmail(request.getEmail());
            maker.verifyPassword(request.getPassword());
            MakerLoginResponse response = MakerLoginResponse.fromMaker(maker);
            return response;
    }

    public ExchangeInformationResponse exchangeView(final UUID makerId) {
        Maker maker = makerRepository.findById(makerId).orElseThrow(
                () -> new MakerNotFoundException("id: " + makerId + " not found")
        );
        ExchangeInformationResponse response = ExchangeInformationResponse.fromMaker(
                maker
        );
        return response;
    }


}
