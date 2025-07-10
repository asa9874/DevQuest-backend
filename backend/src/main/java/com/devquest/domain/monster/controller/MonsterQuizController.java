package com.devquest.domain.monster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.monster.dto.responseDto.QuizWithOutAnswerResponseDto;
import com.devquest.domain.monster.service.MonsterQuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monsters")
public class MonsterQuizController implements MonsterQuizApi {
    private final MonsterQuizService monsterQuizService;

    // 몬스터별 퀴즈 조회
    @GetMapping("/{monsterId}/quizzes")
    public ResponseEntity<List<QuizWithOutAnswerResponseDto>> getQuizzesByMonsterId(
            @PathVariable(name = "monsterId") Long monsterId) {
        List<QuizWithOutAnswerResponseDto> quizzes = monsterQuizService.getQuizzesByMonsterId(monsterId);
        return ResponseEntity.ok(quizzes);
    }

    // 몬스터에 퀴즈 등록
    @PostMapping("/{monsterId}/quizzes/{quizId}")
    public ResponseEntity<Void> registerQuizForMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @PathVariable(name = "quizId") Long quizId) {
        monsterQuizService.AddQuizToMonster(monsterId, quizId);
        return ResponseEntity.ok().build();
    }

    // 몬스터에 퀴즈 삭제
    @DeleteMapping("/{monsterId}/quizzes/{quizId}")
    public ResponseEntity<Void> deleteQuizForMonster(
            @PathVariable(name = "monsterId") Long monsterId,
            @PathVariable(name = "quizId") Long quizId) {
        monsterQuizService.DeleteQuizFromMonster(monsterId, quizId);
        return ResponseEntity.ok().build();
    }
}
