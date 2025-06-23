package com.devquest.domain.guild.dto.responseDto;

import java.time.LocalDateTime;

import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;

public record GuildMemberResponseDto(
        Long id,
        Long memberId,
        String memberName,
        GuildMemberStatus status,
        GuildMemberRole role,
        LocalDateTime joinedAt) {
}
