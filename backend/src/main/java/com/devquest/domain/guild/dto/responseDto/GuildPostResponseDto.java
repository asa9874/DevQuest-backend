package com.devquest.domain.guild.dto.responseDto;

import java.time.LocalDateTime;

public record GuildPostResponseDto(
    Long id,
    String title,
    String content,
    Long guildId,
    String guildName,
    Long authorId,
    String authorName,
    LocalDateTime createdAt
) {
    
}
