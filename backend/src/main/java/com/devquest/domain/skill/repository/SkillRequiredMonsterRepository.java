package com.devquest.domain.skill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.monster.model.Monster;
import com.devquest.domain.skill.model.SkillRequiredMonster;

public interface SkillRequiredMonsterRepository extends JpaRepository<SkillRequiredMonster, Long> {

    @Query("""
            SELECT m FROM SkillRequiredMonster srm
            JOIN srm.requiredMonster m
            WHERE srm.skill.id = :skillId
            """)
    List<Monster> findAllBySkillId(
            @Param("skillId") Long skillId);

    boolean existsBySkill_IdAndRequiredMonster_Id(Long skillId, Long requiredMonsterId);

    void deleteBySkill_IdAndRequiredMonster_Id(Long skillId, Long requiredMonsterId);

    @Query("""
            SELECT CASE WHEN COUNT(srm) > 0 THEN true ELSE false END
            FROM SkillRequiredMonster srm
            WHERE srm.skill.id = :skillId
            AND srm.requiredMonster.id NOT IN (
                SELECT mc.monster.id
                FROM MonsterChallenge mc
                WHERE mc.member.id = :memberId
                AND mc.isSuccess = true
            )
            """)
    boolean existsAnyRequiredMonsterNotDefeatedByMember(@Param("memberId") Long memberId, @Param("skillId") Long skillId);

    @Query("""
            SELECT srm.requiredMonster.id
            FROM SkillRequiredMonster srm
            WHERE srm.skill.id = :skillId
            AND srm.requiredMonster.id NOT IN (
                SELECT mc.monster.id
                FROM MonsterChallenge mc
                WHERE mc.member.id = :memberId
                AND mc.isSuccess = true
            )
            """)
    List<Long> findMissingRequiredMonsterIds(@Param("memberId") Long memberId, @Param("skillId") Long skillId);

}
