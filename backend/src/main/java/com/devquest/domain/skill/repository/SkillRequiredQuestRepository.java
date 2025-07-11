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
    
    @Query("""
            SELECT CASE WHEN COUNT(srq) > 0 THEN true ELSE false END
            FROM SkillRequiredQuest srq
            WHERE srq.skill.id = :skillId
            AND srq.requiredQuest.id NOT IN (
                SELECT qc.quest.id
                FROM QuestChallenge qc
                WHERE qc.member.id = :memberId
                AND qc.status = com.devquest.domain.quest.model.QuestStatus.COMPLETED
            )
            """)
    boolean existsAnyRequiredQuestNotCompletedByMember(@Param("memberId") Long memberId, @Param("skillId") Long skillId);
    
    @Query("""
            SELECT srq.requiredQuest.id
            FROM SkillRequiredQuest srq
            WHERE srq.skill.id = :skillId
            AND srq.requiredQuest.id NOT IN (
                SELECT qc.quest.id
                FROM QuestChallenge qc
                WHERE qc.member.id = :memberId
                AND qc.status = com.devquest.domain.quest.model.QuestStatus.COMPLETED
            )
            """)
    List<Long> findMissingRequiredQuestIds(@Param("memberId") Long memberId, @Param("skillId") Long skillId);
}
