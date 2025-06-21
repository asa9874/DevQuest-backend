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
        String startedAt,
        String EndAt

) {
    public QuestChallengeResponseDto(Long id, Long questId, String title, String description, String status,
            String createrName, Long createrId, LocalDateTime startedAt, LocalDateTime EndAt) {
        this(id, questId, title, description, status, createrName, createrId,
                startedAt.toString(),
                EndAt != null ? EndAt.toString() : "");
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
                questChallenge.getStartedAt(),
                questChallenge.getEndAt());
    }
}
