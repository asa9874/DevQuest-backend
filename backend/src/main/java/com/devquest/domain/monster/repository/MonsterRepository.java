package com.devquest.domain.monster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.monster.model.Monster;

public interface MonsterRepository extends JpaRepository<Monster, Long> {
    boolean existsByName(String name);
    boolean existsByIdAndCreaterId(Long monsterId, Long memberId);
}
