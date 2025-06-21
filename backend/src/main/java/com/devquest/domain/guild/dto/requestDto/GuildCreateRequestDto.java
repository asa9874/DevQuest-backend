package com.devquest.domain.guild.dto.requestDto;

public record GuildCreateRequestDto(
    String name,
    String description
) {
    public GuildCreateRequestDto {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("길드 이름은 비어있을 수 없습니다");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("길드 설명은 비어있을 수 없습니다");
        }
        if (name.length() < 2 || name.length() > 20) {
            throw new IllegalArgumentException("길드 이름은 2자 이상 20자 이하이어야 합니다");
        }
        if (description.length() < 5 || description.length() > 100) {
            throw new IllegalArgumentException("길드 설명은 5자 이상 100자 이하이어야 합니다");
        }
    }
} 
