package com.devquest.domain.monster.dto.responseDto;

import java.time.LocalDateTime;

import com.devquest.domain.monster.model.MonsterChallenge;

public record MonsterChallengeResponseDto(
    Long id,
    Long monsterId,
    String monsterName,
    Long memberId,
    String memberName,
    LocalDateTime challengedAt,
    Boolean isSuccess,
    Integer correctCount,
    Integer requiredCorrectCount
) {
    public static MonsterChallengeResponseDto fromEntity(MonsterChallenge challenge, Integer correctCount) {
        return new MonsterChallengeResponseDto(
            challenge.getId(),
            challenge.getMonster().getId(),
            challenge.getMonster().getName(),
            challenge.getMember().getId(),
            challenge.getMember().getName(),
            challenge.getChallengedAt(),
            challenge.getIsSuccess(),
            correctCount,
            challenge.getMonster().getRequired_correct_count()
        );
    }
}
