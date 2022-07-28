package io.wisoft.testermatchingplatform.domain.apply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    @EntityGraph("applyWithTester")
    Page<Apply> findByQuest_Id(Long id, Pageable pageable);

    @EntityGraph("applyWithTester")
    Optional<Apply> findById(Long id);

    @EntityGraph("applyWithTester")
    Page<Apply> findByTesterId(Long Id, Pageable pageable);
}
