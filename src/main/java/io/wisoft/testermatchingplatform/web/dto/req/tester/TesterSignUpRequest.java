package io.wisoft.testermatchingplatform.web.dto.req.tester;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.wisoft.testermatchingplatform.handler.validator.image.Custom;
import io.wisoft.testermatchingplatform.handler.validator.image.Image;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Getter
@Setter
public class TesterSignUpRequest {

    @Email
    @NotBlank(message = "해당 항목을 필수 항목입니다.")
    private String email;

    @NotBlank(message = "해당 항목을 필수 항목입니다.")
    private String password;

    @NotBlank(message = "해당 항목을 필수 항목입니다.")
    private String nickname;

    @NotBlank(message = "해당 항목을 필수 항목입니다.")
    private String phoneNumber;

    private Long preferCategoryId;

    private String introMessage;

    @Image(groups = Custom.class)
    @NotNull(message = "프로필 이미지를 지정해주세요.", groups = Default.class)
    private MultipartFile introPicture;

}
