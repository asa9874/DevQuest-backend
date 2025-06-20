package com.devquest.domain.quest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.quest.model.QuestLike;

public interface QuestLikeRepository extends JpaRepository<QuestLike, Long> {
    Optional<QuestLike> findByMemberIdAndQuestId(Long memberId, Long questId);
    boolean existsByMemberIdAndQuestId(Long memberId, Long questId);
}
