package com.devquest.domain.skill.repository;

import com.devquest.domain.skill.model.MemberSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {
}
