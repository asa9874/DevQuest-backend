package com.devquest.domain.skill.repository;

import com.devquest.domain.skill.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);
    boolean existsByIdAndCreaterId(Long id, Long createrId);
}
