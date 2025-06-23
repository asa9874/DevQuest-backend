package com.devquest.domain.guild.dto.requestDto;

public record GuildPostCreateRequestDto(
    Long guildId,
    String title,
    String content
) {
} 