package io.wisoft.testermatchingplatform.service;

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
public class UpdateTestIncludeImageRequest {
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

    public Tests updateTest(Tests test, String imageURL) {
        test.updateTest(
                title,
                content,
                imageURL,
                reward,
                limitPerformer,
                LocalDate.parse(recruitmentTimeStart, DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(recruitmentTimeLimit, DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(durationTimeStart, DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(durationTimeLimit, DateTimeFormatter.ISO_LOCAL_DATE)
        );

        return test;
    }
}
