package com.devquest.domain.monster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.monster.model.QuizChallenge;

public interface QuizChallengeRepository extends JpaRepository<QuizChallenge, Long> {
    int countByMonsterChallengeIdAndIsCorrectTrue(Long monsterChallengeId);

    List<QuizChallenge> findByMonsterChallengeId(Long monsterChallengeId);
    boolean existsByIdAndMonsterChallengeMemberId(Long quizChallengeId, Long memberId);
}
