package io.wisoft.testermatchingplatform.domain.test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, String> {

    @EntityGraph("questFound")
    Page<Test> findAll(Pageable pageable);

    // 카테고리로 퀘스트 조회
    Page<Test> findByCategoryId(Long id, Pageable pageable);

    // ntc로 퀘스트 조회
    Page<Test> findByQuestMakerId(Long id, Pageable pageable);


    //퀘스트 save

    // 아이디와 연관된 퀘스트 모두 제거
    void deleteAllByQuestMaker_Id(Long id);
}
