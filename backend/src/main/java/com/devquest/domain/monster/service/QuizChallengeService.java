package com.devquest.domain.monster.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.monster.dto.requestDto.QuizChallengeSolveRequestDto;
import com.devquest.domain.monster.dto.responseDto.QuizChallengeResponseDto;
import com.devquest.domain.monster.model.QuizChallenge;
import com.devquest.domain.monster.repository.MonsterChallengeRepository;
import com.devquest.domain.monster.repository.QuizChallengeRepository;
import com.devquest.domain.monster.util.MonsterQuizValidator;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizChallengeService {
    private final QuizChallengeRepository quizChallengeRepository;
    private final MonsterChallengeRepository monsterChallengeRepository;
    private final MonsterQuizValidator monsterValidator;

    public void solveQuizChallenge(
            Long quizChallengeId,
            QuizChallengeSolveRequestDto requestDto,
            Long memberId) {
        QuizChallenge quizChallenge = quizChallengeRepository.findById(quizChallengeId)
                .orElseThrow(() -> new EntityNotFoundException("퀴즈 도전이 존재하지 않습니다."));

        if (quizChallenge.isSolved()) {
            throw new IllegalArgumentException("이미 퀴즈를 풀었습니다.");
        }

        if (!monsterValidator.isQuizChallengeOwner(quizChallengeId, memberId)) {
            throw new AccessDeniedException("퀴즈 도전자는 본인만 가능합니다.");
        }

        quizChallenge.setSelectedOption(requestDto.selectedOption());
        quizChallengeRepository.save(quizChallenge);
    }

    public List<QuizChallengeResponseDto> getQuizChallengeByMonsterChallengeId(Long challengeId, Long memberId) {
        if (!monsterChallengeRepository.existsById(challengeId)) {
            throw new EntityNotFoundException("존재하지 않는 챌린지입니다.");
        }

        if (!monsterValidator.isMonsterChallengeOwner(challengeId, memberId)) {
            throw new AccessDeniedException("해당 챌린지에 대한 퀴즈 도전 정보를 조회할 권한이 없습니다.");
        }

        return quizChallengeRepository.findByMonsterChallengeId(challengeId)
                .stream()
                .map(QuizChallengeResponseDto::from)
                .toList();
    }
}
