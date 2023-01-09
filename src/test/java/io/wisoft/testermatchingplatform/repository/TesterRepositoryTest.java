package io.wisoft.testermatchingplatform.repository;

import io.wisoft.testermatchingplatform.domain.Tester;
import io.wisoft.testermatchingplatform.handler.exception.service.TesterNotFoundException;
import io.wisoft.testermatchingplatform.web.dto.request.CreateTesterRequest;
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
class TesterRepositoryTest {
    @Autowired
    TesterRepository testerRepository;
    @Autowired
    EntityManager em;
    Tester createdTester;
    UUID createdTesterId;

    @BeforeEach
    public void prepareTest() {
        String email = "testTester@naver.comm";
        String password = "1234567";
        String nickname = "testman123";
        String phoneNumber = "010-1234-1242";
        String introMessage = "안녕하세용 ㅎㅎ 테스터입니다";
        CreateTesterRequest request = CreateTesterRequest.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        );
        createdTester = request.toTester();
        em.persist(createdTester);
        createdTesterId = createdTester.getId();
        em.flush();
        em.clear();

    }

    @Test
    @DisplayName("Tester 저장 테스트 - 성공")
    public void saveTesterSuccessTest() throws Exception {
        //given
        String email = "testTester@naver.com";
        String password = "1234";
        String nickname = "testman";
        String phoneNumber = "010-1234-1234";
        String introMessage = "안녕하세용 테스터입니다";
        CreateTesterRequest request = CreateTesterRequest.newInstance(
                email,
                password,
                nickname,
                phoneNumber,
                introMessage
        );
        Tester tester = request.toTester();
        //when
        Tester storedTester = testerRepository.save(tester);

        //then
        assertEquals(tester.getId(), storedTester.getId());
        assertEquals(tester.getEmail(), storedTester.getEmail());
    }

    @Test
    @DisplayName("Tester 저장 테스트 - 실패(Unique Key 중복)")
    public void saveTesterFailTest() throws Exception {
        //given
        String overlapEmail = "testTester@naver.comm";
        String overlapPassword = "12345";
        String overlapNickname = "testman123";
        String overlapPhoneNumber = "010-1234-1242";
        String overlapIntroMessage = "안녕하세용 ㅎㅎ 테스터입니다";
        CreateTesterRequest request = CreateTesterRequest.newInstance(
                overlapEmail,
                overlapPassword,
                overlapNickname,
                overlapPhoneNumber,
                overlapIntroMessage
        );
        Tester overlapTester = request.toTester();
        //when
        //then
        testerRepository.save(overlapTester);
        assertThrows(PersistenceException.class,
                () -> em.flush()
        );
    }

    @Test
    @DisplayName("Tester ID로 조회 테스트 - 성공")
    public void findByIdSuccessTest() throws Exception {
        //given
        String email = "testTester@naver.comm";
        String password = "1234567";
        String nickname = "testman123";

        System.out.println("status: " + testerRepository.findById(createdTesterId).isEmpty());
        //when
        Tester tester = testerRepository.findById(createdTesterId).get();

        //then
        assertEquals(email, tester.getEmail());
        assertEquals(password, tester.getPassword());
        assertEquals(nickname, tester.getNickname());
    }

    @Test
    @DisplayName("Tester Email로 조회 테스트 - 성공")
    public void findByEmailSuccessTest() throws Exception {
        //given
        String email = "testTester@naver.comm";
        String password = "1234567";

        //when
        Tester tester = testerRepository.findByEmail(email).get();

        //then
        assertEquals(email, tester.getEmail());
        assertEquals(password, tester.getPassword());
        assertEquals(createdTesterId, tester.getId());
    }

    @Test
    @DisplayName("총 개수 조회 테스트 - 성공")
    public void countSuccessTest() throws Exception {
        //given
        long expectCount = 5;
        //when
        long count = testerRepository.count();
        //then
        assertEquals(expectCount, count);
    }

    @Test
    @DisplayName("총 Tester 조회 테스트 - 성공")
    public void findAllSuccessTest() throws Exception {
        //given
        int expectSize = 5;

        //when
        List<Tester> all = testerRepository.findAll();

        //then
        assertEquals(expectSize, all.size());

    }



}