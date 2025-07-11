package com.devquest.domain.monster.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devquest.domain.monster.dto.requestDto.QuizChallengeSolveRequestDto;
import com.devquest.domain.monster.dto.responseDto.QuizChallengeResponseDto;
import com.devquest.domain.monster.model.QuizChallenge;
import com.devquest.domain.monster.repository.MonsterChallengeRepository;
import com.devquest.domain.monster.repository.QuizChallengeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizChallengeService {
    private final QuizChallengeRepository quizChallengeRepository;
    private final MonsterChallengeRepository monsterChallengeRepository;

    public void solveQuizChallenge(
            Long quizChallengeId,
            QuizChallengeSolveRequestDto requestDto,
            Long memberId) {
        QuizChallenge quizChallenge = quizChallengeRepository.findById(quizChallengeId)
                .orElseThrow(() -> new IllegalArgumentException("퀴즈 도전이 존재하지 않습니다."));

        if (quizChallenge.isSolved()) {
            throw new IllegalArgumentException("이미 퀴즈를 풀었습니다.");
        }

        if (memberId != quizChallenge.getMonsterChallenge().getMember().getId()) {
            throw new IllegalArgumentException("퀴즈 도전자는 본인만 가능합니다.");
        }

        quizChallenge.setSelectedOption(requestDto.selectedOption());
        quizChallengeRepository.save(quizChallenge);
    }

    public List<QuizChallengeResponseDto> getQuizChallengeByMonsterChallengeId(Long challengeId, Long memberId) {
        if (!monsterChallengeRepository.existsById(challengeId)) {
            throw new IllegalArgumentException("존재하지 않는 챌린지입니다.");
        }

        if (!monsterChallengeRepository.existsByIdAndMemberId(challengeId, memberId)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        return quizChallengeRepository.findByMonsterChallengeId(challengeId)
                .stream()
                .map(QuizChallengeResponseDto::from)
                .toList();
    }
}
