package io.wisoft.testermatchingplatform.web.dto.resp.submit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubmitTesterListResponse {
    private String testerName;
    private int status;

    public static SubmitTesterListResponse from(final String testerName,final int status){
        return new SubmitTesterListResponse(
                testerName,
                status
        );
    }
}
