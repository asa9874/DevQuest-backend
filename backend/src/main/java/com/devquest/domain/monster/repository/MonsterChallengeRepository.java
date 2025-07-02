package com.devquest.domain.monster.repository;

import com.devquest.domain.monster.model.MonsterChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonsterChallengeRepository extends JpaRepository<MonsterChallenge, Long> {
}
