package com.devquest.domain.guild.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.dto.requestDto.GuildCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.model.GuildMember;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildPostCommentRepository;
import com.devquest.domain.guild.repository.GuildPostRepository;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.guildChat.repository.GuildChatRoomMessageRepository;
import com.devquest.domain.guildChat.repository.GuildChatRoomRepository;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;
import com.devquest.global.exception.customException.DuplicateDataException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildService {
    private final GuildRepository guildRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final GuildPostRepository guildPostRepository;
    private final GuildPostCommentRepository guildPostCommentRepository;
    private final GuildChatRoomRepository guildChatRoomRepository;
    private final GuildChatRoomMessageRepository guildChatRoomMessageRepository;

    private final MemberRepository memberRepository;

    public void createGuild(GuildCreateRequestDto requestDto,
            Long memberId) {
        Member leader = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원 정보가 없습니다."));
        if (guildRepository.existsByName(requestDto.name())) {
            throw new DuplicateDataException("이미 존재하는 길드 이름입니다.");
        }
        Guild guild = Guild.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .leader(leader)
                .build();
        guildRepository.save(guild);

        GuildMember guildMember = GuildMember.builder()
                .guild(guild)
                .member(leader)
                .role(GuildMemberRole.OWNER)
                .status(GuildMemberStatus.ACTIVE)
                .build();
        guildMemberRepository.save(guildMember);
    }

    public List<GuildResponseDto> getAllGuilds() {
        return guildRepository.findAll().stream()
                .map(GuildResponseDto::from)
                .collect(Collectors.toList());
    }

    public GuildResponseDto getGuildById(Long guildId) {
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드입니다."));
        return GuildResponseDto.from(guild);
    }

    public Page<GuildResponseDto> searchGuilds(
            String name, Pageable pageable) {
        Page<Guild> guilds = guildRepository.findByNameContaining(name, pageable);
        return guilds.map(GuildResponseDto::from);
    }

    public GuildResponseDto updateGuild(
            Long guildId, GuildUpdateRequestDto requestDto) {
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드입니다."));

        if (!AuthUtil.isAdminOrEqualMember(guild.getLeader().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (guildRepository.existsByName(requestDto.name())) {
            throw new DuplicateDataException("이미 존재하는 길드 이름입니다.");
        }

        guild.updateGuild(requestDto.name(), requestDto.description());
        guildRepository.save(guild);
        return GuildResponseDto.from(guild);
    }

    public void deleteGuild(Long guildId) {
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드입니다."));
        if (!AuthUtil.isAdminOrEqualMember(guild.getLeader().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        //중간 테이블 삭제쓰~
        guildMemberRepository.deleteAllByGuildId(guildId);
        guildPostCommentRepository.deleteAllByGuildId(guildId);
        guildPostRepository.deleteAllByGuildId(guildId);
        guildChatRoomMessageRepository.deleteAllByGuildId(guildId);
        guildChatRoomRepository.deleteAllByGuildId(guildId);

        guildRepository.delete(guild);
    }
}
