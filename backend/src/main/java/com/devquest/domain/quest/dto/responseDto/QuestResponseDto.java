package com.devquest.domain.quest.dto.responseDto;

import java.time.LocalDateTime;

import com.devquest.domain.quest.model.Quest;

public record QuestResponseDto(
    Long id,
    String title,
    String description,
    String createrName,
    Long createrId,
    String createdAt
) {
    public QuestResponseDto(Long id, String title, String description,String createrName, Long createrId, LocalDateTime createdAt) {
        this(id, title, description, createrName, createrId, createdAt.toString());
    }

    public static QuestResponseDto from(
        Quest quest
    ) {
        return new QuestResponseDto(
            quest.getId(),
            quest.getTitle(),
            quest.getDescription(),
            quest.getCreater().getName(),
            quest.getCreater().getId(),
            quest.getCreatedAt().toString()
        );
    }
}
