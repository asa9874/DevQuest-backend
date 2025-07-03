package com.devquest.domain.skill.repository;

import com.devquest.domain.skill.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
