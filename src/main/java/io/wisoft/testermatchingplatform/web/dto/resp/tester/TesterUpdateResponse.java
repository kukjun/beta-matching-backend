package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class TesterUpdateResponse {
    Long id;

    public static TesterUpdateResponse from(final Long id){
        return new TesterUpdateResponse(
                id
        );
    }
}
