package com.devquest.domain.monster.repository;

import com.devquest.domain.monster.model.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
