package io.wisoft.testermatchingplatform.service.maker;

import io.wisoft.testermatchingplatform.web.dto.request.CashRequest;
import io.wisoft.testermatchingplatform.web.dto.request.PointRequest;
import io.wisoft.testermatchingplatform.web.dto.request.maker.*;
import io.wisoft.testermatchingplatform.web.dto.response.AccountRequest;
import io.wisoft.testermatchingplatform.web.dto.response.AccountResponse;
import io.wisoft.testermatchingplatform.web.dto.response.CashResponse;
import io.wisoft.testermatchingplatform.web.dto.response.PointResponse;
import io.wisoft.testermatchingplatform.web.dto.response.maker.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface MakerAuthService {

    CreateTestResponse createTest(UUID makerId, @RequestBody CreateTestRequest request);

    PatchTestResponse patchTest(UUID makerId, UUID testId, @RequestBody PatchTestRequest request);

    findTestsResponse findTests(UUID makerId);

    FindApplyResponse findApply(UUID testId);

    FindPerformListResponse findPerformList(UUID testId);

    FindCompleteTesterListResponse findCompleteTester(UUID testId);

    FindPaymentResponse findPayment(UUID makerId);

    ChangeApplyStateResponse changeApplyState(@RequestBody CompleteRequest completeRequest);

    CreateTestersReviewResponse createTestersReview(@RequestBody CreateTestersReviewRequest request, UUID makerId);

    ConfirmApplyResponse confirmApply(UUID testId, @RequestBody ConfirmApplyRequest request);


    AccountResponse updateAccount(UUID testerId, AccountRequest accountRequest);

    CashResponse changePointToCash(UUID makerId, PointRequest pointRequest);

    PointResponse changeCashToPoint(UUID makerId, CashRequest cashRequest);
}
