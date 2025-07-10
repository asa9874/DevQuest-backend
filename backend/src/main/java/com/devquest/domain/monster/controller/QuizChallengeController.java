package com.devquest.domain.monster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.requestDto.QuizChallengeSolveRequestDto;
import com.devquest.domain.monster.service.QuizChallengeService;
import com.devquest.global.jwt.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monsters/")
public class QuizChallengeController implements QuizChallengeApi {
    private final QuizChallengeService quizChallengeService;

    // 퀴즈 도전
    @PostMapping("/quizzes/challenges/{quizChallengeId}")
    public ResponseEntity<Void> solveQuizChallenge(
            @PathVariable(name = "quizChallengeId") Long quizChallengeId,
            @Valid @RequestBody QuizChallengeSolveRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails member) {
        quizChallengeService.solveQuizChallenge(quizChallengeId, requestDto, member.getId());
        return ResponseEntity.ok().build();
    }

}
