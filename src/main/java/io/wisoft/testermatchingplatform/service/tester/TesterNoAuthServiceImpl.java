package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.domain.tester.Tester;
import io.wisoft.testermatchingplatform.domain.tester.TesterRepository;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterNotFoundException;
import io.wisoft.testermatchingplatform.web.dto.request.tester.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.tester.TesterRegisterRequest;
import io.wisoft.testermatchingplatform.web.dto.response.tester.TesterLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.tester.TesterRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TesterNoAuthServiceImpl implements TesterNoAuthService{

    private final TesterRepository testerRepository;

    @Transactional
    public TesterRegisterResponse register(TesterRegisterRequest request) {
        Tester entity = TesterRegisterRequest.toEntity(request);
        return new TesterRegisterResponse(
                testerRepository.save(entity).getId()
        );
    }

    @Transactional
    public TesterLoginResponse login(TesterLoginRequest request) {
        Tester tester = testerRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new TesterNotFoundException("존재하지 않는 이메일입니다.")
        );
        tester.checkPassword(request.getPassword());
        return new TesterLoginResponse(
                tester.getId(), tester.getNickname()
        );
    }

}
