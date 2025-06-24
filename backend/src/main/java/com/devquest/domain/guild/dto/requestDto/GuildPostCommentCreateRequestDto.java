package com.devquest.domain.guild.dto.requestDto;

public record GuildPostCommentCreateRequestDto(
        Long guildPostId,
        String content) {

}
