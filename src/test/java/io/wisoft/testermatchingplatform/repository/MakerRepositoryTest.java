package io.wisoft.testermatchingplatform.repository;


import io.wisoft.testermatchingplatform.domain.Maker;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MakerRepositoryTest {

    @Autowired
    MakerRepository makerRepository;

    @Autowired
    EntityManager em;
    Maker createdMaker;
    UUID createMakerId;

    @BeforeEach
    public void prepareTest() {
        String email = "TestMaker@naver.com";
        String password = "1234";
        String nickname = "maker입니당";
        String phoneNumber = "010-2341-1422";
        String company = "Naver";
        createdMaker = CreateMakerRequest.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                company
        ).toMaker();
        em.persist(createdMaker);
        createMakerId = createdMaker.getId();
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("Maker 저장 테스트 - 성공")
    public void saveMakerSuccessTest() throws Exception {
        //given
        String email = "TestMaker123@naver.com";
        String password = "1234123";
        String nickname = "maker입니당123";
        String phoneNumber = "010-2341-1234";
        String company = "Naver123";
        Maker maker = CreateMakerRequest.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                company
        ).toMaker();
        //when
        Maker storedMaker = makerRepository.save(maker);

        //then
        assertEquals(maker, storedMaker);
    }

    @Test
    @DisplayName("Maker 저장 테스트 - 실패(Unique 중복)")
    public void saveMakerFailTest() throws Exception {
        //given
        String overlapEmail = "TestMaker@naver.com";
        String overlapPassword = "1234123";
        String overlapNickname = "maker입니당";
        String overlapPhoneNumber = "010-2341-1234";
        String overlapCompany = "Naver123";
        Maker maker = CreateMakerRequest.newInstance(
                overlapEmail,
                overlapPassword,
                overlapNickname,
                overlapPhoneNumber,
                overlapCompany
        ).toMaker();
        makerRepository.save(maker);
        //when
        //then
        assertThrows(PersistenceException.class,
                () -> em.flush());
    }

    @Test
    @DisplayName("Maker Email 조회 테스트 - 성공")
    public void findByEmailSuccessTest() throws Exception {
        //given
        String email = "TestMaker@naver.com";
        String password = "1234";

        //when
        Maker maker = makerRepository.findByEmail(email);

        //then
        assertEquals(createMakerId, maker.getId());
        assertEquals(email, maker.getEmail());
        assertEquals(password, maker.getPassword());
    }

    @Test
    @DisplayName("Maker 전체 조회 테스트 - 성공")
    public void findAllSuccessTest() throws Exception {
        //given
        int expectSize = 2;

        //when
        List<Maker> all = makerRepository.findAll();

        //then
        assertEquals(expectSize, all.size());
    }

    @Test
    @DisplayName("전체 개수를 조회하는 테스트 - 성공")
    public void countSuccessTest() throws Exception {
        //given
        long expectCount = 2L;

        //when
        long count = makerRepository.count();

        //then
        assertEquals(expectCount, count);
    }

}