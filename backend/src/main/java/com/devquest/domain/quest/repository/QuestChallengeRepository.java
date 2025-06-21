package com.devquest.domain.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.quest.model.QuestChallenge;
import com.devquest.domain.quest.model.QuestStatus;

public interface QuestChallengeRepository extends JpaRepository<QuestChallenge, Long> {
    void deleteByMemberId(Long memberId);
    void deleteByQuestId(Long questId);
    
    boolean existsByQuestIdAndMemberIdAndStatus(Long questId, Long memberId, QuestStatus status);
}
