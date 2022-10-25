package io.wisoft.testermatchingplatform.service.maker;

import io.wisoft.testermatchingplatform.domain.maker.Maker;
import io.wisoft.testermatchingplatform.domain.maker.MakerRepository;
import io.wisoft.testermatchingplatform.web.dto.request.MakerLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.MakerRegisterRequest;
import io.wisoft.testermatchingplatform.web.dto.response.maker.MakerLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.maker.MakerRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MakerNoAuthServiceImpl implements MakerNoAuthService {
    private final MakerRepository makerRepository;

    // 회원가입 메서드 생성
    @Override
    @Transactional
    public MakerRegisterResponse makerRegister(MakerRegisterRequest request) {
        Maker entity = MakerRegisterRequest.toEntity(request);
        return new MakerRegisterResponse(
                makerRepository.save(entity).getId()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public MakerLoginResponse makerLogin(MakerLoginRequest request) {
        Maker maker = makerRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new RuntimeException("login fail")
        );
        System.out.println(maker.getEmail());
        maker.checkPassword(request.getPassword());
        return new MakerLoginResponse(
                maker.getId(), maker.getNickname()
        );

    }
}
