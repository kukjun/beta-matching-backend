package io.wisoft.testermatchingplatform.web.dto.request.maker;

import io.wisoft.testermatchingplatform.domain.maker.Maker;
import io.wisoft.testermatchingplatform.domain.test.Test;
import io.wisoft.testermatchingplatform.domain.test.TestRelateTime;
import io.wisoft.testermatchingplatform.handler.validator.image.Custom;
import io.wisoft.testermatchingplatform.handler.validator.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;

@Getter
@AllArgsConstructor
public class CreateTestRequest {

    private String title;
    private String recruitmentTimeStart;
    private String recruitmentTimeLimit;
    private String durationTimeStart;
    private String durationTimeLimit;
    private String content;
    private int reward;
    private int participantCapacity;

    @Image(groups = Custom.class)
    private MultipartFile symbolImage;

    public Test toEntity(Maker maker, String symbolImageRoot) {
        Test test = new Test();
        try {
            test.setTitle(title);
            test.setParticipantCapacity(participantCapacity);
            test.setSymbolImageRoot(symbolImageRoot);
            test.setContent(content);
            test.setReward(reward);
            test.setMaker(maker);
            test.setTestRelateTime(
                    new TestRelateTime(
                            new SimpleDateFormat("yyyy-MM-dd").parse(recruitmentTimeStart),
                            new SimpleDateFormat("yyyy-MM-dd").parse(recruitmentTimeLimit),
                            new SimpleDateFormat("yyyy-MM-dd").parse(durationTimeStart),
                            new SimpleDateFormat("yyyy-MM-dd").parse(durationTimeLimit)
                    )
            );
            System.out.println(test.getTestRelateTime().getDurationTimeStart());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;

    }


}
