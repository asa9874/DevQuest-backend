package com.devquest.domain.skill.repository;

import com.devquest.domain.monster.model.Monster;
import com.devquest.domain.skill.model.SkillRequiredMonster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
