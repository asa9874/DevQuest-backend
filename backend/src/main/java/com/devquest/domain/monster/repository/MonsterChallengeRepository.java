package com.devquest.domain.monster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.monster.dto.responseDto.MonsterChallengeResponseDto;
import com.devquest.domain.monster.model.MonsterChallenge;

public interface MonsterChallengeRepository extends JpaRepository<MonsterChallenge, Long> {
    boolean existsByMonsterIdAndMemberIdAndIsSuccessIsNull(Long monsterId, Long memberId);

    int countByMonsterIdAndIsSuccessIsNull(Long monsterId);

    boolean existsByIdAndMemberId(Long challengeId, Long memberId);

    List<MonsterChallenge> findByMemberIdAndIsSuccessIsNull(Long memberId);

    @Query("""
            SELECT new com.devquest.domain.monster.dto.responseDto.MonsterChallengeResponseDto(
                mc.id,
                mc.monster.id,
                mc.monster.name,
                mc.member.id,
                mc.member.name,
                mc.challengedAt,
                mc.isSuccess,
                CAST(COALESCE(
                    (SELECT COUNT(qc) FROM QuizChallenge qc WHERE qc.monsterChallenge.id = mc.id AND qc.isCorrect = true),
                    0
                ) AS integer),
                mc.monster.required_correct_count
            )
            FROM MonsterChallenge mc
            WHERE mc.member.id = :memberId AND mc.isSuccess IS NULL
            """)
    List<MonsterChallengeResponseDto> findOngoingChallengesDtoByMemberId(
            @Param("memberId") Long memberId);

    @Query("""
            SELECT new com.devquest.domain.monster.dto.responseDto.MonsterChallengeResponseDto(
                mc.id,
                mc.monster.id,
                mc.monster.name,
                mc.member.id,
                mc.member.name,
                mc.challengedAt,
                mc.isSuccess,
                CAST(COALESCE(
                    (SELECT COUNT(qc) FROM QuizChallenge qc WHERE qc.monsterChallenge.id = mc.id AND qc.isCorrect = true),
                    0
                ) AS integer),
                mc.monster.required_correct_count
            )
            FROM MonsterChallenge mc
            """)
    List<MonsterChallengeResponseDto> findAllDto();
}
