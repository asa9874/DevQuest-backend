package com.devquest.domain.monster.repository;

import com.devquest.domain.monster.model.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonsterRepository extends JpaRepository<Monster, Long> {
    boolean existsByName(String name);
    boolean existsByIdAndCreaterId(Long monsterId, Long memberId);
}
