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
        if (title.length() < 2 || title.length() > 100) {
            throw new IllegalArgumentException("제목은 2자 이상 100자 이하이어야 합니다.");
        }
        if (description.length() < 5 || description.length() > 500) {
            throw new IllegalArgumentException("설명은 5자 이상 500자 이하이어야 합니다.");
        }
    }
}
