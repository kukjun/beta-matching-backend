package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.Mission;
import io.wisoft.testermatchingplatform.domain.Tester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ApplyInformationRepositoryTest {

    @Autowired
    ApplyInformationRepository applyInformationRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("신청 정보 저장 테스트 - 성공")
    public void saveApplyInformationSuccessTest() throws Exception {
        //given
        Mission mission = em.find(Mission.class, UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5f0"));
        Tester tester = em.find(Tester.class, UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1"));
        ApplyInformation applyInformation = ApplyInformation.newInstance(mission, tester);

        //when
        ApplyInformation storedApplyInformation = applyInformationRepository.save(applyInformation);

        //then
        assertEquals(applyInformation.getId(), storedApplyInformation.getId());
        assertEquals(mission, storedApplyInformation.getMission());
        assertEquals(tester, storedApplyInformation.getTester());
    }

    @Test
    @DisplayName("전체 신청정보 조회 테스트 - 성공")
    public void findAllSuccessTest() throws Exception {
        //given
        int expectedApplyInformationSize = 17;

        //when
        List<ApplyInformation> all = applyInformationRepository.findAll();

        //then
        assertEquals(expectedApplyInformationSize, all.size());

    }

    @Test
    @DisplayName("testerId로 해당 ApplyInformation을 조회 테스트 - 성공")
    public void findByTesterIdSuccessTest() throws Exception {
        //given
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        int expectSize = 4;

        //when
        List<ApplyInformation> applyInformations = applyInformationRepository.findByTesterId(testerId);

        //then
        assertEquals(expectSize, applyInformations.size());
    }

    @Test
    @DisplayName("ApplyInformationId로 해당 ApplyInformation을 제거 테스트 - 성공")
    public void deleteByIdSuccessTest() throws Exception {
        //given
        UUID applyInformationId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5d0");

        //when
        applyInformationRepository.deleteById(applyInformationId);
        ApplyInformation deleteApplyInformation = em.find(ApplyInformation.class, applyInformationId);

        //then
        assertNull(deleteApplyInformation);
    }

    @Test
    @DisplayName("TestserId, MissionId를 이용해서 ApplyInformation 제거 테스트 - 성공")
    public void deleteByTesterIdAndMissionIdSuccessTest() throws Exception {
        //given
        UUID applyInformationId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5d0");
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c1");
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");

        //when
        applyInformationRepository.deleteApplyInformationByTesterIdAndMissionId(
                testerId, missionId
        );
        ApplyInformation deleteApplyInformation = em.find(ApplyInformation.class, applyInformationId);

        //then
        assertNull(deleteApplyInformation);
    }

    @Test
    @DisplayName("TesterId, MissionId를 이용해서 ApplyInformation 조회 테스트 - 성공")
    public void findApplyInformationByTesterIdByMissionIdSuccessTest() throws Exception {
        //given
        UUID missionId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5c1");
        UUID testerId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5b1");
        UUID expectedApplyInformationId = UUID.fromString("5c3c4895-8ca6-435a-95f8-487a0784b5d0");

        //when
        ApplyInformation applyInformation = applyInformationRepository.findApplyInformationByTesterIdAndMissionId(
                testerId, missionId
        ).get();

        //then
        assertEquals(expectedApplyInformationId, applyInformation.getId());
    }



}