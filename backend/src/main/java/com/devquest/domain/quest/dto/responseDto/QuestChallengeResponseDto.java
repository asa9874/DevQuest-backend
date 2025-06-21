package com.devquest.domain.quest.dto.responseDto;

import java.time.LocalDateTime;

import com.devquest.domain.quest.model.QuestChallenge;

public record QuestChallengeResponseDto(
        Long id,
        Long questId,
        String title,
        String description,
        String status,
        String createrName,
        Long createrId,
        String ChallengerName,
        Long ChallengerId,
        String startedAt,
        String EndAt

) {
    public QuestChallengeResponseDto(
            Long id,
            Long questId,
            String title,
            String description,
            String status,
            String createrName,
            Long createrId,
            String challengerName,
            Long challengerId,
            LocalDateTime startedAt,
            LocalDateTime endAt) {
        this(
                id,
                questId,
                title,
                description,
                status,
                createrName,
                createrId,
                challengerName,
                challengerId,
                startedAt != null ? startedAt.toString() : null,
                endAt != null ? endAt.toString() : null);
    }

    public static QuestChallengeResponseDto from(QuestChallenge questChallenge) {
        return new QuestChallengeResponseDto(
                questChallenge.getId(),
                questChallenge.getQuest().getId(),
                questChallenge.getQuest().getTitle(),
                questChallenge.getQuest().getDescription(),
                questChallenge.getStatus().name(),
                questChallenge.getQuest().getCreater().getName(),
                questChallenge.getQuest().getCreater().getId(),
                questChallenge.getMember() != null ? questChallenge.getMember().getName() : null,
                questChallenge.getMember() != null ? questChallenge.getMember().getId() : null,
                questChallenge.getStartedAt(),
                questChallenge.getEndAt()
        );
    }
}
