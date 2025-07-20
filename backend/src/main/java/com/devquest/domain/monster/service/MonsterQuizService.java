package com.devquest.domain.monster.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.monster.dto.responseDto.QuizWithOutAnswerResponseDto;
import com.devquest.domain.monster.model.Monster;
import com.devquest.domain.monster.model.MonsterQuiz;
import com.devquest.domain.monster.model.Quiz;
import com.devquest.domain.monster.repository.MonsterQuizRepository;
import com.devquest.domain.monster.repository.MonsterRepository;
import com.devquest.domain.monster.repository.QuizRepository;
import com.devquest.domain.monster.util.MonsterQuizValidator;
import com.devquest.global.exception.customException.DuplicateDataException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonsterQuizService {
    private final MonsterQuizRepository monsterQuizRepository;
    private final MonsterRepository monsterRepository;
    private final QuizRepository quizRepository;
    private final MonsterQuizValidator monsterValidator;

    public void AddQuizToMonster(Long monsterId, Long quizId) {
        if (monsterQuizRepository.existsByMonsterIdAndQuizId(monsterId, quizId)) {
            throw new DuplicateDataException("이미 등록된 퀴즈입니다.");
        }

        Monster monster = monsterRepository.findById(monsterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 몬스터입니다."));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 퀴즈입니다."));

        if (!monsterValidator.isMonsterOwner(monsterId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("해당 몬스터에 퀴즈를 등록할 권한이 없습니다.");
        }

        MonsterQuiz monsterQuiz = MonsterQuiz.builder()
                .monster(monster)
                .quiz(quiz)
                .build();
        monsterQuizRepository.save(monsterQuiz);
    }

    @Transactional
    public void DeleteQuizFromMonster(Long monsterId, Long quizId) {
        if (!monsterQuizRepository.existsByMonsterIdAndQuizId(monsterId, quizId)) {
            throw new EntityNotFoundException("등록되지 않은 퀴즈입니다.");
        }

        if (!monsterRepository.existsById(monsterId)) {
            throw new EntityNotFoundException("존재하지 않는 몬스터입니다.");
        }

        if (!monsterValidator.isMonsterOwner(monsterId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("해당 몬스터에 퀴즈를 삭제할 권한이 없습니다.");
        }

        monsterQuizRepository.deleteByMonsterIdAndQuizId(monsterId, quizId);
    }

    public List<QuizWithOutAnswerResponseDto> getQuizzesByMonsterId(Long monsterId) {
        if (!monsterRepository.existsById(monsterId)) {
            throw new EntityNotFoundException("존재하지 않는 몬스터입니다.");
        }

        List<Quiz> quizzes = quizRepository.findAllByMonsterId(monsterId);
        return quizzes.stream()
                .map(QuizWithOutAnswerResponseDto::from)
                .toList();

    }
}
