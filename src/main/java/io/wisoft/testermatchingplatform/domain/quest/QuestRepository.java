package io.wisoft.testermatchingplatform.domain.quest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {

    @EntityGraph("questFound")
    Page<Quest> findAll(Pageable pageable);

    // 카테고리로 퀘스트 조회
    Page<Quest> findByCategoryId(Long id,Pageable pageable);

    // ntc로 퀘스트 조회
    Page<Quest> findByQuestMakerId(Long id,Pageable pageable);

    // 아이디로 퀘스트 하나 조회
    Optional<Quest> findById(Long id);

    //퀘스트 save

    // 아이디와 연관된 퀘스트 모두 제거
    void deleteAllByQuestMaker_Id(Long id);
}
