package com.devquest.domain.guild.dto.responseDto;


public record GuildPostCommentResponseDto(
        Long id,
        String content,
        Long guildPostId,
        Long memberId,
        String memberName,
        String createdAt
) {
}
