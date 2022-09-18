package io.wisoft.testermatchingplatform.web.dto.req.tester;

import io.wisoft.testermatchingplatform.handler.validator.image.Custom;
import io.wisoft.testermatchingplatform.handler.validator.image.Image;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class QuestApplyRequest {

    @NotBlank(message = "해당 항목은 필수 항목입니다.")
    private Long questId;

    @Image(groups = Custom.class)
    private MultipartFile requireConditionSubmit;

    @Image(groups = Custom.class)
    private MultipartFile preferenceConditionSubmit;

}
