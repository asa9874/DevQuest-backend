package com.devquest.domain.monster.repository;

import com.devquest.domain.monster.model.QuizChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizChallengeRepository extends JpaRepository<QuizChallenge, Long> {
    int countByMonsterChallengeIdAndIsCorrectTrue(Long monsterChallengeId);
}
