package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.maker.Maker;
import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
import io.wisoft.testermatchingplatform.web.dto.request.MakerLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.MakerRegisterRequest;
import io.wisoft.testermatchingplatform.web.dto.response.MakerLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.MakerRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MakerNoAuthService {
    private final MakerRepository makerRepository;

    // 회원가입 메서드 생성
    @Transactional
    public MakerRegisterResponse register(MakerRegisterRequest request) {
        Maker entity = MakerRegisterRequest.toEntity(request);
        return new MakerRegisterResponse(
                makerRepository.save(entity).getId()
        );
    }


    @Transactional
    public MakerLoginResponse login(MakerLoginRequest request) {
        return null;
    }
}
