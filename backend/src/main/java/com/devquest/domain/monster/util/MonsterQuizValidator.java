package com.devquest.domain.monster.util;

import org.springframework.stereotype.Component;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.monster.repository.MonsterChallengeRepository;
import com.devquest.domain.monster.repository.MonsterRepository;
import com.devquest.domain.monster.repository.QuizChallengeRepository;
import com.devquest.domain.monster.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonsterQuizValidator {
    private final MonsterRepository monsterRepository;
    private final QuizRepository quizRepository;
    private final QuizChallengeRepository quizChallengeRepository;
    private final MonsterChallengeRepository monsterChallengeRepository;

    /**
     * 몬스터의 소유자인지 확인
     */
    public boolean isMonsterOwner(Long monsterId, Long memberId) {
        return AuthUtil.isAdmin() ||
                monsterRepository.existsByIdAndCreaterId(monsterId, memberId);
    }

    /**
     * 퀴즈 챌린지의 소유자인지 확인
     */
    public boolean isQuizChallengeOwner(Long quizChallengeId, Long memberId) {
        return AuthUtil.isAdmin() ||
                quizChallengeRepository.existsByIdAndMonsterChallengeMemberId(quizChallengeId, memberId);
    }

    /**
     * 몬스터 챌린지의 소유자인지 확인
     */
    public boolean isMonsterChallengeOwner(Long monsterChallengeId, Long memberId) {
        return AuthUtil.isAdmin() ||
                monsterChallengeRepository.existsByIdAndMemberId(monsterChallengeId, memberId);
    }

    /**
     * 퀴즈의 소유자인지 확인
     */
    public boolean isQuizOwner(Long quizId, Long memberId) {
        return AuthUtil.isAdmin() ||
                quizRepository.existsByIdAndCreaterId(quizId, memberId);
    }

}
