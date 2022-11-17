package io.wisoft.testermatchingplatform.web.dto.request.maker;

import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRelateTime;
import io.wisoft.testermatchingplatform.handler.validator.image.Custom;
import io.wisoft.testermatchingplatform.handler.validator.image.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;

@Getter
@Setter
public class PatchTestExceptImageRequest {

    private String title;
    private String recruitmentTimeStart;
    private String recruitmentTimeLimit;
    private String durationTimeStart;
    private String durationTimeLimit;
    private String content;
    private int reward;
    private int participantCapacity;

    public Test toEntity(Test test) {
        try {
            test.setTitle(title);
            test.setParticipantCapacity(participantCapacity);
            test.setContent(content);
            test.setReward(reward);
            test.setTestRelateTime(
                    new TestRelateTime(
                            new SimpleDateFormat("yyyy-MM-dd").parse(recruitmentTimeStart),
                            new SimpleDateFormat("yyyy-MM-dd").parse(recruitmentTimeLimit),
                            new SimpleDateFormat("yyyy-MM-dd").parse(durationTimeStart),
                            new SimpleDateFormat("yyyy-MM-dd").parse(durationTimeLimit)
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }
}
