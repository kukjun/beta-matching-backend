package io.wisoft.testermatchingplatform.service.nologin;

import io.wisoft.testermatchingplatform.web.dto.response.nologin.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


public interface NoAuthService {

    @Transactional
    public CountResponse counts() ;

    @Transactional
    public List<TestListResponse> testList();

    @Transactional
    public List<FastDeadlineResponse> fastDeadline();

    @Transactional
    public List<ManyApplyResponse> manyApply();

    @Transactional
    public DetailTestResponse detailTest(final UUID id);
}
