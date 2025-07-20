package com.devquest.domain.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.skill.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);
    boolean existsByIdAndCreaterId(Long id, Long createrId);
}
