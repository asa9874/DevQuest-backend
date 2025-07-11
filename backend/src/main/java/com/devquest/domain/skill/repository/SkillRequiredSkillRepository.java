package com.devquest.domain.skill.repository;

import com.devquest.domain.skill.model.Skill;
import com.devquest.domain.skill.model.SkillRequiredSkill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkillRequiredSkillRepository extends JpaRepository<SkillRequiredSkill, Long> {

    @Query("""
            SELECT s FROM SkillRequiredSkill srs
            JOIN srs.requiredSkill s
            WHERE srs.skill.id = :skillId
            """)
    List<Skill> findAllBySkillId(
            @Param("skillId") Long skillId);

    boolean existsBySkillIdAndRequiredSkillId(Long skillId, Long requiredSkillId);

    void deleteBySkillIdAndRequiredSkillId(Long skillId, Long requiredSkillId);
}
