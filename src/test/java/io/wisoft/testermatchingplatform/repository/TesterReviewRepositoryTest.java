package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.MakerReview;
import io.wisoft.testermatchingplatform.domain.TesterReview;
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
class TesterReviewRepositoryTest {

    @Autowired
    TesterReviewRepository testerReviewRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Tester Review 등록 테스트")
    public void saveTesterReviewSuccessTest() throws Exception {
        UUID applyInformationId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5e5");
        ApplyInformation applyInformation = em.find(ApplyInformation.class, applyInformationId);
        //given
        TesterReview testerReview = TesterReview.newInstance(
                applyInformation,
                4,
                "좋았습니다."
        );

        //when
        TesterReview storedTesterReview = testerReviewRepository.save(testerReview);

        //then
        assertEquals(testerReview.getId(), storedTesterReview.getId());
        assertEquals(testerReview.getStarPoint(), storedTesterReview.getStarPoint());
        assertEquals(testerReview.getComment(), storedTesterReview.getComment());
    }
}