package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.MakerReview;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MakerReviewRepositoryTest {

    @Autowired
    MakerReviewRepository makerReviewRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Maker Review 등록 테스트")
    public void saveMakerReviewSuccessTest() throws Exception {
        UUID applyInformationId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5e5");
        ApplyInformation applyInformation = em.find(ApplyInformation.class, applyInformationId);
        //given
        MakerReview makerReview = MakerReview.newInstance(
                applyInformation,
                4,
                "좋았습니다."
        );

        //when
        MakerReview storeMakerReview = makerReviewRepository.save(makerReview);

        //then
        assertEquals(makerReview.getId(), storeMakerReview.getId());
        assertEquals(makerReview.getStarPoint(), storeMakerReview.getStarPoint());
        assertEquals(makerReview.getComment(), storeMakerReview.getComment());
    }


}