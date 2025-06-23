package com.devquest.domain.guild.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.dto.responseDto.GuildMemberResponseDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.repository.GuildMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildMemberService {
    private final GuildMemberRepository guildMemberRepository;

    public List<GuildMemberResponseDto> getGuildMembers(
            Long guildId,
            GuildMemberStatus status,
            GuildMemberRole role,
            Long memberId) {
        if (!guildMemberRepository.existsByMemberIdAndStatus(memberId, GuildMemberStatus.ACTIVE)
                || !AuthUtil.isAdmin()) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        List<GuildMemberResponseDto> responseDtos = guildMemberRepository.findGuildMembersByGuildIdAndStatusAndRole(
                guildId, status, role);
        return responseDtos;
    }

    public List<GuildResponseDto> getGuildsByMemberId(
            Long memberId,
            GuildMemberStatus status,
            GuildMemberRole role) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        List <GuildResponseDto> responseDtos =
                guildMemberRepository.findGuildsByMemberIdAndStatusAndRole(memberId, status, role);
        return responseDtos;
    }


}
