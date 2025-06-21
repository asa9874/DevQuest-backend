package com.devquest.domain.guild.dto.responseDto;

import com.devquest.domain.guild.model.Guild;

public record GuildResponseDto(
    Long id,
    String name,
    String description,
    String leaderName
) {
    public static GuildResponseDto from(Guild guild) {
        return new GuildResponseDto(
            guild.getId(),
            guild.getName(),
            guild.getDescription(),
            guild.getLeader().getName()
        );
    }
}
