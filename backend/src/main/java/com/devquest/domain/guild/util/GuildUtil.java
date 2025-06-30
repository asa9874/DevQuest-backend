package com.devquest.domain.guild.util;

import org.springframework.stereotype.Component;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GuildUtil {
    private final GuildMemberRepository guildMemberRepository;

    public boolean isAdminOrGuildAdmin(Long memberId, Long guildId) {
        return AuthUtil.isAdmin()
                ||
                guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                        guildId, memberId, GuildMemberStatus.ACTIVE, GuildMemberRole.ADMIN)
                ||
                guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                        guildId, memberId, GuildMemberStatus.ACTIVE, GuildMemberRole.OWNER);
    }

    public boolean isAdminOrGuildMember(Long memberId, Long guildId) {
        return AuthUtil.isAdmin()
                ||
                guildMemberRepository.existsByGuildIdAndMemberIdAndStatus(
                        guildId, memberId, GuildMemberStatus.ACTIVE);
    }
}
