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
            GuildMember guildmember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 길드 멤버입니다"));
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
        if (!AuthUtil.isAdminOrEqualMember(requestMemberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        GuildMember guildMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 길드 멤버입니다"));
        if (guildMember.getStatus() != GuildMemberStatus.ACTIVE) {
            throw new IllegalArgumentException("이미 탈퇴한 길드입니다");
        }
        if (guildMember.getRole() == GuildMemberRole.OWNER) {
            throw new IllegalArgumentException("길드장은 탈퇴할 수 없습니다");
        }
        guildMember.leave();
        guildMemberRepository.save(guildMember);
    }

    public void changeGuildMemberRole(
            Long guildId,
            Long memberId,
            GuildMemberRole role,
            Long requestMemberId) {
        if (!AuthUtil.isAdminOrEqualMember(requestMemberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        GuildMember requestMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, requestMemberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 요청자 멤버입니다"));

        GuildMember guildMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 길드 멤버입니다"));
        if (guildMember.getRole() == role) {
            throw new IllegalArgumentException("이미 해당 역할입니다");
        }
        if(AuthUtil.isAdmin()){ //관리자면 아래 다 무시하고 그냥;
            guildMember.changeRole(role);
            guildMemberRepository.save(guildMember);
            return;
        }

        if (requestMember.getRole() != GuildMemberRole.ADMIN
                && requestMember.getRole() != GuildMemberRole.OWNER) {
            throw new IllegalArgumentException("어드민 권한이 없습니다");
        }

        if (guildMember.getStatus() != GuildMemberStatus.ACTIVE) {
            throw new IllegalArgumentException("활동 중인 길드 멤버가 아닙니다");
        }

        if (guildMember.getRole() == GuildMemberRole.OWNER) {
            throw new IllegalArgumentException("길드장은 역할을 변경할 수 없습니다");
        }

        if (guildMember.getRole() == GuildMemberRole.ADMIN && requestMember.getRole() != GuildMemberRole.OWNER) {
            throw new IllegalArgumentException("관리자유저는 오직 길드장만 변경할 수 있습니다");
        }

        if (role == null) {
            throw new IllegalArgumentException("변경할 역할을 지정해야 합니다");
        }

        if (role == GuildMemberRole.OWNER) {
            throw new IllegalArgumentException("길드장은 역할을 변경할 수 없습니다");
        }

        if (role == GuildMemberRole.ADMIN && requestMember.getRole() != GuildMemberRole.OWNER) {
            throw new IllegalArgumentException("관리자 권한은 오직 길드장만 부여할 수 있습니다");
        }

        guildMember.changeRole(role);
        guildMemberRepository.save(guildMember);
    }

    public void banGuildMember(
            Long guildId,
            Long memberId,
            Long requestMemberId) {
        if (!AuthUtil.isAdminOrEqualMember(requestMemberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        GuildMember guildMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 길드 멤버입니다"));
        if (guildMember.getRole() == GuildMemberRole.OWNER || guildMember.getRole() == GuildMemberRole.ADMIN) {
            throw new IllegalArgumentException("길드장과 어드민은 밴할 수 없습니다");
        }
        if (guildMember.getStatus() == GuildMemberStatus.BANNED) {
            throw new IllegalArgumentException("이미 밴된 길드 멤버입니다");
        }
        guildMember.ban();
        guildMemberRepository.save(guildMember);
    }

    public void unbanGuildMember(
            Long guildId,
            Long memberId,
            Long requestMemberId) {
        if (!AuthUtil.isAdminOrEqualMember(requestMemberId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        GuildMember guildMember = guildMemberRepository.findByGuildIdAndMemberId(guildId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 길드 멤버입니다"));
        if (guildMember.getStatus() != GuildMemberStatus.BANNED) {
            throw new IllegalArgumentException("밴되지 않은 길드 멤버입니다");
        }
        guildMember.unban();
        guildMemberRepository.save(guildMember);
    }
}

