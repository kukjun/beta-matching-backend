package io.wisoft.testermatchingplatform.web.dto.request.maker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

public class ConfirmApplyRequest {
    private List<UUID> approveTesterList;

    public ConfirmApplyRequest() {
        approveTesterList = new ArrayList<>();
    }
}
