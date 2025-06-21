package com.devquest.domain.quest.dto.requestDto;

public record QuestUpdateRequestDto(
    String title,
    String description
) {
    public QuestUpdateRequestDto {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수 입력값입니다.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("설명은 필수 입력값입니다.");
        }
    }
}
