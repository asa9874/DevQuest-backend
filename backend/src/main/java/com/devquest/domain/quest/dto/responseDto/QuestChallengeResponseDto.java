package com.devquest.domain.quest.dto.responseDto;

import java.time.LocalDateTime;

import com.devquest.domain.quest.model.QuestChallenge;
import com.devquest.domain.quest.model.QuestStatus;

public record QuestChallengeResponseDto(
        Long id,
        Long questId,
        String questTitle,
        String questDescription,
        QuestStatus status,
        String questCreaterName,
        Long questCreaterId,
        String memberName,
        Long memberId,
        LocalDateTime startedAt,
        LocalDateTime endAt
) {
    public static QuestChallengeResponseDto from(QuestChallenge questChallenge) {
        return new QuestChallengeResponseDto(
                questChallenge.getId(),
                questChallenge.getQuest().getId(),
                questChallenge.getQuest().getTitle(),
                questChallenge.getQuest().getDescription(),
                questChallenge.getStatus(),
                questChallenge.getQuest().getCreater().getName(),
                questChallenge.getQuest().getCreater().getId(),
                questChallenge.getMember() != null ? questChallenge.getMember().getName() : null,
                questChallenge.getMember() != null ? questChallenge.getMember().getId() : null,
                questChallenge.getStartedAt(),
                questChallenge.getEndAt()
        );
    }
}
