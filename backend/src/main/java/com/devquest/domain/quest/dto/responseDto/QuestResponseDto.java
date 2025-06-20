package com.devquest.domain.quest.dto.responseDto;

import com.devquest.domain.quest.model.Quest;

public record QuestResponseDto(
    Long id,
    String title,
    String description,
    String createrName,
    Long createrId,
    Long likeCount,
    String createdAt
) {
    public static QuestResponseDto from(
        Quest quest,
        Long likeCount
    ) {
        return new QuestResponseDto(
            quest.getId(),
            quest.getTitle(),
            quest.getDescription(),
            quest.getCreater().getName(),
            quest.getCreater().getId(),
            likeCount,
            quest.getCreatedAt().toString()
        );
    }
}
