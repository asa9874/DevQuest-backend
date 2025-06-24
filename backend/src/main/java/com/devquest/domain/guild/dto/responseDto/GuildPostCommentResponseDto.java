package com.devquest.domain.guild.dto.responseDto;

import java.time.LocalDateTime;

public record GuildPostCommentResponseDto(
        Long id,
        String content,
        Long guildPostId,
        Long authorId,
        String authorName,
        LocalDateTime createdAt
) {
}
