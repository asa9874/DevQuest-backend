package com.devquest.domain.skill.repository;

import com.devquest.domain.quest.model.Quest;
import com.devquest.domain.skill.model.SkillRequiredQuest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkillRequiredQuestRepository extends JpaRepository<SkillRequiredQuest, Long> {

    @Query("""
            SELECT q FROM SkillRequiredQuest srq
            JOIN srq.requiredQuest q
            WHERE srq.skill.id = :skillId
            """)
    List<Quest> findAllBySkillId(
            @Param("skillId") Long skillId);

    boolean existsBySkill_IdAndRequiredQuest_Id(Long skillId, Long questId);

    void deleteBySkill_IdAndRequiredQuest_Id(Long skillId, Long questId);
}
