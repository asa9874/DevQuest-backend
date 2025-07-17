package com.devquest.domain.monster.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.domain.monster.dto.responseDto.MonsterChallengeResponseDto;
import com.devquest.domain.monster.model.Monster;
import com.devquest.domain.monster.model.MonsterChallenge;
import com.devquest.domain.monster.model.Quiz;
import com.devquest.domain.monster.model.QuizChallenge;
import com.devquest.domain.monster.repository.MonsterChallengeRepository;
import com.devquest.domain.monster.repository.MonsterRepository;
import com.devquest.domain.monster.repository.QuizChallengeRepository;
import com.devquest.domain.monster.repository.QuizRepository;
import com.devquest.domain.monster.util.MonsterQuizValidator;
import com.devquest.global.exception.customException.DuplicateDataException;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonsterChallengeService {
    private final MonsterChallengeRepository monsterChallengeRepository;
    private final MemberRepository memberRepository;
    private final MonsterRepository monsterRepository;
    private final QuizChallengeRepository quizChallengeRepository;
    private final QuizRepository quizRepository;
    private final MonsterQuizValidator monsterValidator;

    public void createChallengeForMonster(
            Long monsterId,
            Long memberId) {
        if (monsterChallengeRepository.existsByMonsterIdAndMemberIdAndIsSuccessIsNull(monsterId, memberId)) {
            throw new DuplicateDataException("이미 진행 중인 도전이 있습니다.");
        }

        Monster monster = monsterRepository.findById(monsterId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 몬스터입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        MonsterChallenge monsterChallenge = MonsterChallenge.builder()
                .monster(monster)
                .member(member)
                .build();
        monsterChallengeRepository.save(monsterChallenge);

        List<Quiz> quizChallenges = quizRepository.findAllByMonsterId(monsterId);
        for (Quiz quiz : quizChallenges) {
            QuizChallenge quizChallenge = QuizChallenge.builder()
                    .quiz(quiz)
                    .monsterChallenge(monsterChallenge)
                    .build();
            quizChallengeRepository.save(quizChallenge);
        }
    }

    public void completeChallenge(
            Long monsterChallengeId) {
        MonsterChallenge monsterChallenge = monsterChallengeRepository.findById(monsterChallengeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 몬스터 도전입니다."));

        if (!monsterValidator.isQuizChallengeOwner(monsterChallengeId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("해당 도전 정보를 수정할 권한이 없습니다.");
        }

        if (monsterChallenge.getIsSuccess() != null) {
            throw new IllegalStateException("이미 완료된 도전입니다.");
        }

        int requriedCorrectCount = monsterChallenge.getMonster().getRequired_correct_count();
        int correctCount = quizChallengeRepository.countByMonsterChallengeIdAndIsCorrectTrue(monsterChallengeId);
        if (correctCount >= requriedCorrectCount) {
            monsterChallenge.setSuccess(true);
        } else {
            monsterChallenge.setSuccess(false);
        }
        monsterChallengeRepository.save(monsterChallenge);
    }

    public MonsterChallengeResponseDto getChallengeById(Long challengeId, Long memberId) {
        MonsterChallenge challenge = monsterChallengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 몬스터 도전입니다."));

        if (!monsterValidator.isQuizChallengeOwner(challengeId, memberId)) {
            throw new AccessDeniedException("해당 도전 정보를 조회할 권한이 없습니다.");
        }

        Integer correctCount;
        if (challenge.getIsSuccess() == null) {
            correctCount = null;
        } else {
            correctCount = quizChallengeRepository.countByMonsterChallengeIdAndIsCorrectTrue(challengeId);
        }
        MonsterChallengeResponseDto responseDto = MonsterChallengeResponseDto.fromEntity(challenge, correctCount);

        return responseDto;
    }
}
