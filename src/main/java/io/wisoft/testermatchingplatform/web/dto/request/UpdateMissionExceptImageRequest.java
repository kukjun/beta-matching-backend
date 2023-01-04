package io.wisoft.testermatchingplatform.web.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMissionExceptImageRequest {
    private final String title;
    private final String recruitmentTimeStart;
    private final String recruitmentTimeLimit;
    private final String durationTimeStart;
    private final String durationTimeLimit;
    private final String content;
    private final long reward;
    private final int limitPerformer;


    public static UpdateMissionExceptImageRequest newInstance(
            final String title,
            final String recruitmentTimeStart,
            final String recruitmentTimeLimit,
            final String durationTimeStart,
            final String durationTimeLimit,
            final String content,
            final long reward,
            final int limitPerformer
    ) {
        UpdateMissionExceptImageRequest request = new UpdateMissionExceptImageRequest(
                title,
                recruitmentTimeStart,
                recruitmentTimeLimit,
                durationTimeStart,
                durationTimeLimit,
                content,
                reward,
                limitPerformer
        );
        return request;
    }
}
