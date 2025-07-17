package com.devquest.domain.guild.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.dto.responseDto.GuildMemberResponseDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import jakarta.persistence.EntityNotFoundException;
import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.model.GuildMember;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.guild.util.GuildValidator;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.global.exception.customException.DuplicateDataException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildMemberService {
    private final GuildMemberRepository guildMemberRepository;
    private final GuildRepository guildRepository;
    private final MemberRepository memberRepository;
    private final GuildValidator guildValidator;

    public List<GuildMemberResponseDto> getGuildMembers(
            Long guildId,
            GuildMemberStatus status,
            GuildMemberRole role,
            Long memberId) {

        if (!guildValidator.isGuildMember(memberId, guildId)) {
            throw new AccessDeniedException("길드 회원이 아닙니다.");
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
            throw new AccessDeniedException("권한이 없습니다");
        }

        List<GuildResponseDto> responseDtos = guildMemberRepository.findGuildsByMemberIdAndStatusAndRole(memberId,
                status, role);
        return responseDtos;
    }

    public void joinGuild(Long guildId, Long memberId) {
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드입니다"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다"));

        if (guildValidator.isGuildMember(memberId, guildId)) {
            throw new DuplicateDataException("이미 길드에 가입되어 있습니다");
        }

        // 나간 길드원이면 재가입으로 변경함
        if (guildValidator.isLeavedGuildMember(memberId, guildId)) {
            GuildMember guildmember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드 멤버입니다"));
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

    public void leaveGuild(
            Long guildId,
            Long memberId,
            Long requestMemberId) {
        GuildMember guildMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드 멤버입니다"));

        if (!guildValidator.canLeaveGuild(requestMemberId, guildId)) {
            throw new IllegalStateException("길드를 탈퇴할수없습니다.(길드장이거나 길드에 가입되어있지않음)");
        }

        guildMember.leave();
        guildMemberRepository.save(guildMember);
    }

    public void changeGuildMemberRole(
            Long guildId,
            Long memberId,
            GuildMemberRole role,
            Long requestMemberId) {
        GuildMember guildMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드 멤버입니다"));

        if (!guildValidator.canChangeGuildMemberRole(guildMember, guildId, requestMemberId, role)) {
            throw new AccessDeniedException("해당 역할로 변경할 수 없습니다");
        }

        guildMember.changeRole(role);
        guildMemberRepository.save(guildMember);
    }

    public void banGuildMember(
            Long guildId,
            Long memberId,
            Long requestMemberId) {
        if (!guildValidator.isGuildAdmin(requestMemberId, guildId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        GuildMember guildMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드 멤버입니다"));

        if (!guildValidator.canBanGuildMember(guildMember, guildId, requestMemberId)) {
            throw new AccessDeniedException("해당 멤버를 밴할 수 없습니다");
        }

        guildMember.ban();
        guildMemberRepository.save(guildMember);
    }

    public void unbanGuildMember(
            Long guildId,
            Long memberId,
            Long requestMemberId) {
        if (!guildValidator.isGuildAdmin(requestMemberId, guildId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        GuildMember guildMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드 멤버입니다"));

        if (!guildValidator.isBannedGuildMember(memberId, guildId)) {
            throw new IllegalStateException("밴되지 않은 길드 멤버입니다");
        }

        guildMember.unban();
        guildMemberRepository.save(guildMember);
    }
}
