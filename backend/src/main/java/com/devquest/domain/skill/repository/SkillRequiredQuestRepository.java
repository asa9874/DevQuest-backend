package com.devquest.domain.skill.repository;

import com.devquest.domain.skill.model.SkillRequiredQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRequiredQuestRepository extends JpaRepository<SkillRequiredQuest, Long> {
}
