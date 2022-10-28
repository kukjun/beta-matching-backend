package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.web.dto.request.tester.TesterLoginRequest;
import io.wisoft.testermatchingplatform.web.dto.request.tester.TesterRegisterRequest;
import io.wisoft.testermatchingplatform.web.dto.response.tester.TesterLoginResponse;
import io.wisoft.testermatchingplatform.web.dto.response.tester.TesterRegisterResponse;

import javax.transaction.Transactional;


public interface TesterNoAuthService{
    
    // 테스터 회원가입
    @Transactional
    public TesterRegisterResponse register(TesterRegisterRequest request);

    // 테스터 로그인
    @Transactional
    public TesterLoginResponse login(TesterLoginRequest request) ;
}
