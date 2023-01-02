package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.handler.exception.LoginException;
import io.wisoft.testermatchingplatform.repository.MakerRepository;
import io.wisoft.testermatchingplatform.web.dto.request.*;
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
public class MakerService {

    private final MakerRepository makerRepository;

    @Transactional
    public CreateMakerResponse createMaker(final CreateMakerRequest request) {
        Maker maker = request.toMaker();

        makerRepository.save(maker);
        CreateMakerResponse response = CreateMakerResponse.fromMaker(maker);
        return response;
    }

    @Transactional
    public AccountResponse updateAccount(final UUID makerId, final AccountRequest request) {
        Maker maker = makerRepository.findById(makerId);
        maker.changeAccount(request.getAccount());

        AccountResponse response = AccountResponse.fromMaker(maker);
        return response;
    }

    @Transactional
    public ChangePointToCashResponse changePointToCash(final UUID makerId, final ChangePointToCashRequest request) {
        Maker maker = makerRepository.findById(makerId);
        long cash = maker.pointToCash(request.getPoint());

        ChangePointToCashResponse response = ChangePointToCashResponse.newInstance(cash);
        return response;
    }

    @Transactional
    public ChangeCashToPointResponse changeCashToPoint(final UUID makerId, final ChangeCashToPointRequest request) {
        Maker maker = makerRepository.findById(makerId);
        long point = maker.cashToPoint(request.getCash());

        ChangeCashToPointResponse response = ChangeCashToPointResponse.newInstance(point);
        return response;
    }

    public MakerLoginResponse login(final MakerLoginRequest request) {
        try {
            Maker maker = makerRepository.findByEmail(request.getEmail());
            maker.checkPassword(request.getPassword());
            MakerLoginResponse response = MakerLoginResponse.fromMaker(maker);
            return response;
        } catch (NoResultException no) {
            throw new LoginException();
        } catch (NonUniqueResultException non) {
            throw new RuntimeException();
        }
    }

    public ExchangeInformationResponse exchangeView(final UUID makerId) {
        Maker maker = makerRepository.findById(makerId);
        ExchangeInformationResponse response = ExchangeInformationResponse.fromMaker(
                maker.getPoint(),
                maker.getAccount()
        );
        return response;
    }


}
