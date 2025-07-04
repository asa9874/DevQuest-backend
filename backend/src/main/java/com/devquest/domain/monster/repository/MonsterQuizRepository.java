package com.devquest.domain.monster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devquest.domain.monster.model.MonsterQuiz;

@Repository
public interface MonsterQuizRepository extends JpaRepository<MonsterQuiz, Long> {
}
