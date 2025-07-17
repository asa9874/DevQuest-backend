package com.devquest.domain.monster.repository;

import com.devquest.domain.monster.model.Quiz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("""
            SELECT q FROM Quiz q
            JOIN MonsterQuiz mq ON q.id = mq.quiz.id
            WHERE mq.monster.id = :monsterId
                """)
    List<Quiz> findAllByMonsterId(@Param("monsterId") Long monsterId);

    boolean existsByIdAndCreaterId(Long quizId, Long memberId);
}
