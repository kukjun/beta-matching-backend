package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.domain.Tests;
import io.wisoft.testermatchingplatform.handler.validator.image.Custom;
import io.wisoft.testermatchingplatform.handler.validator.image.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTestRequest {
    private final String title;
    private final String recruitmentTimeStart;
    private final String recruitmentTimeLimit;
    private final String durationTimeStart;
    private final String durationTimeLimit;
    private final String content;
    private final int reward;
    private final int limitPerformer;
    @Image(groups = Custom.class)
    private final MultipartFile image;

    public Tests toTest(Maker maker, String imageURL) {

        Tests test = Tests.newInstance(
                title,
                content,
                imageURL,
                reward,
                limitPerformer,
                maker,
                LocalDate.parse(recruitmentTimeStart, DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(recruitmentTimeLimit, DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(durationTimeStart, DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(durationTimeLimit, DateTimeFormatter.ISO_LOCAL_DATE)
        );
        return test;
    }

    public static CreateTestRequest newInstance(
            final String title,
            final String recruitmentTimeStart,
            final String recruitmentTimeLimit,
            final String durationTimeStart,
            final String durationTimeLimit,
            final String content,
            final int reward,
            final int limitPerformer,
            final MultipartFile symbolImage
    ) {
        CreateTestRequest request = new CreateTestRequest(
                title,
                recruitmentTimeStart,
                recruitmentTimeLimit,
                durationTimeStart,
                durationTimeLimit,
                content,
                reward,
                limitPerformer,
                symbolImage
        );
        return request;
    }
}
