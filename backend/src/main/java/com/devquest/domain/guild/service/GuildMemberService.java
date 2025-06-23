package com.devquest.domain.guild.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.dto.responseDto.GuildMemberResponseDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.model.GuildMember;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildMemberService {
    private final GuildMemberRepository guildMemberRepository;
    private final GuildRepository guildRepository;
    private final MemberRepository memberRepository;

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

        List<GuildResponseDto> responseDtos = guildMemberRepository.findGuildsByMemberIdAndStatusAndRole(memberId,
                status, role);
        return responseDtos;
    }

    public void joinGuild(Long guildId, Long memberId) {
        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 길드입니다"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));

        if (guildMemberRepository.existsByGuildIdAndMemberIdAndStatus(
                guildId, memberId, GuildMemberStatus.ACTIVE)) {
            throw new IllegalArgumentException("이미 가입된 길드입니다");
        }

        if (guildMemberRepository.existsByMemberIdAndStatus(memberId, GuildMemberStatus.LEAVED)) {
            GuildMember guildmember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId);
            guildmember.resign();
            return;
        }

        GuildMember guildMember = GuildMember.builder()
                .guild(guild)
                .member(member)
                .status(GuildMemberStatus.ACTIVE)
                .role(GuildMemberRole.MEMBER)
                .build();
        guildMemberRepository.save(guildMember);
    }
}
