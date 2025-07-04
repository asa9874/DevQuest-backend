package com.devquest.domain.monster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.requestDto.QuizChallengeCreateRequestDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/monsters/")
public class QuizChallengeController {

    // 퀴즈 도전
    @PostMapping("/quizzes/{quizId}/challenges")
    public ResponseEntity<Void> createQuizChallenge(
            @PathVariable(name = "quizId") Long quizId,
            @Valid @RequestBody QuizChallengeCreateRequestDto requestDto) {
        return null;
    }
    
}
