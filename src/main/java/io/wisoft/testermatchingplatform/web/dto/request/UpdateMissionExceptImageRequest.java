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
    private final int reward;
    private final int limitPerformer;


}
