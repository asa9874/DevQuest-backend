package com.devquest.domain.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.quest.model.QuestChallenge;

public interface QuestChallengeRepository extends JpaRepository<QuestChallenge, Long> {
}
