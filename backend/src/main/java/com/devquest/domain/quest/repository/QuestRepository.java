package com.devquest.domain.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.quest.model.Quest;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    boolean existsByTitle(String title);
}
