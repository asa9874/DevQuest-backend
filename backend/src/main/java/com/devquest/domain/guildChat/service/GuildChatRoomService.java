package com.devquest.domain.guildChat.service;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomUpdateRequestDto;
import com.devquest.domain.guildChat.model.GuildChatRoom;
import com.devquest.domain.guildChat.repository.GuildChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildChatRoomService {
    private final GuildChatRoomRepository guildChatRoomRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final GuildRepository guildRepository;

    public void createGuildChatRoom(
            GuildChatRoomCreateRequestDto requestDto,
            Long memberId) {

        if (!AuthUtil.isAdmin()) {
            if (guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                    requestDto.guildId(), memberId, GuildMemberStatus.ACTIVE, GuildMemberRole.ADMIN)
                    || guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                            requestDto.guildId(), memberId, GuildMemberStatus.ACTIVE, GuildMemberRole.OWNER)) {
                throw new IllegalArgumentException("권한이 없습니다.");
            }
        }

        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Guild guild = guildRepository.findById(requestDto.guildId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 길드입니다."));

        if (guildChatRoomRepository.existsByTitleAndGuildId(requestDto.title(), requestDto.guildId())) {
            throw new IllegalArgumentException("이미 존재하는 채팅방 제목입니다.");
        }

        GuildChatRoom guildChatRoom = GuildChatRoom.builder()
                .title(requestDto.title())
                .description(requestDto.description())
                .guild(guild)
                .build();
        guildChatRoomRepository.save(guildChatRoom);
    }

    public void updateGuildChatRoom(
            Long guildChatRoomId,
            GuildChatRoomUpdateRequestDto requestDto,
            Long memberId) {

        GuildChatRoom guildChatRoom = guildChatRoomRepository.findById(guildChatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));
        Long guildId = guildChatRoom.getGuild().getId();
        if (!AuthUtil.isAdmin()) {
            if (guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                    guildId, memberId, GuildMemberStatus.ACTIVE, GuildMemberRole.ADMIN)
                    || guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                            guildId, memberId, GuildMemberStatus.ACTIVE,
                            GuildMemberRole.OWNER)) {
                throw new IllegalArgumentException("권한이 없습니다.");
            }
        }

        if (guildChatRoomRepository.existsByTitleAndGuildId(requestDto.title(), guildId)) {
            throw new IllegalArgumentException("이미 존재하는 채팅방 제목입니다.");
        }

        if (!AuthUtil.isAdminOrEqualMember(guildChatRoom.getGuild().getLeader().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        guildChatRoom.update(requestDto.title(), requestDto.description());
        guildChatRoomRepository.save(guildChatRoom);
    }
}
