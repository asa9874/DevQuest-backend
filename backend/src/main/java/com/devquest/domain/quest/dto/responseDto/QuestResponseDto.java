package com.devquest.domain.quest.dto.responseDto;

public record QuestResponseDto(
    Long id,
    String title,
    String description,
    String createrName,
    Long createrId,
    Long likeCount,
    String createdAt,
    boolean isLikedByMe
) {
}
