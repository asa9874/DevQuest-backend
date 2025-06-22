package com.devquest.domain.guild.dto.responseDto;

public record GuildMemberResponseDto(
        Long id,
        String name,
        String status,
        String joinedAt
) {
    
}
