package com.devquest.domain.quest.dto.responseDto;

import java.time.LocalDateTime;

import com.devquest.domain.quest.model.Quest;

public record QuestWithLikeResponseDto(
    Long id,
    String title,
    String description,
    String createrName,
    Long createrId,
    Long likeCount,
    String createdAt
) {
    public QuestWithLikeResponseDto(Long id, String title, String description,String createrName, Long createrId, Long likeCount, LocalDateTime createdAt) {
        this(id, title, description, createrName, createrId, likeCount, createdAt.toString());
    }

    public static QuestWithLikeResponseDto from(
        Quest quest,
        Long likeCount
    ) {
        return new QuestWithLikeResponseDto(
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
