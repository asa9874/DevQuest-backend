package com.devquest.domain.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.quest.model.QuestLike;

public interface QuestLikeRepository extends JpaRepository<QuestLike, Long> {
    Long countByQuestId(Long questId);
}
