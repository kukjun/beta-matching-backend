package io.wisoft.testermatchingplatform.service.tester;

import io.wisoft.testermatchingplatform.web.dto.response.tester.*;

import javax.transaction.Transactional;
import java.util.UUID;


public interface TesterAuthService {

    // 보유 포인트, 계좌 조회하기
    @Transactional
    public ExchangePointResponse exchangePoint(final UUID testerId);
    // 리뷰쓰고 포인트 받기
    @Transactional
    public CreateReviewResponse createReview(final UUID applyId, final int starPoint, final String comment);

    // 테스트 신청하기
    @Transactional
    public ApplyTestResponse applyTest(final UUID testerId, final UUID testId) throws Exception;

    // 효율성 변환 및 코드 단순화가 필요!! 신청 테스트 리스트 구하기
    @Transactional
    public TesterTestListResponse selectApplyTestList(final UUID id);

    // ?
    public ApplyInformationIdResponse findApplyTest(UUID testerId, UUID testId);
}
