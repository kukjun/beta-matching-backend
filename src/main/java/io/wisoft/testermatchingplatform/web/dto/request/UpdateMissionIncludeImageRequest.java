package io.wisoft.testermatchingplatform.web.dto.request;

import io.wisoft.testermatchingplatform.handler.validator.image.Custom;
import io.wisoft.testermatchingplatform.handler.validator.image.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMissionIncludeImageRequest {
    private final String title;
    private final String recruitmentTimeStart;
    private final String recruitmentTimeLimit;
    private final String durationTimeStart;
    private final String durationTimeLimit;
    private final String content;
    private final long reward;
    private final int limitPerformer;
    @Image(groups = Custom.class)
    private final MultipartFile image;

    public static UpdateMissionIncludeImageRequest newInstance(
            final String title,
            final String recruitmentTimeStart,
            final String recruitmentTimeLimit,
            final String durationTimeStart,
            final String durationTimeLimit,
            final String content,
            final long reward,
            final int limitPerformer,
            final MultipartFile image
    ) {
        UpdateMissionIncludeImageRequest request = new UpdateMissionIncludeImageRequest(
                title,
                recruitmentTimeStart,
                recruitmentTimeLimit,
                durationTimeStart,
                durationTimeLimit,
                content,
                reward,
                limitPerformer,
                image
        );

        return request;
    }

}
