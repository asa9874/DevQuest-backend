package com.devquest.domain.guildChat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.guild.util.GuildUtil;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomResponseDto;
import com.devquest.domain.guildChat.model.GuildChatRoom;
import com.devquest.domain.guildChat.repository.GuildChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildChatRoomService {
    private final GuildChatRoomRepository guildChatRoomRepository;
    private final GuildRepository guildRepository;
    private final GuildUtil guildUtil;

    public void createGuildChatRoom(
            GuildChatRoomCreateRequestDto requestDto,
            Long memberId) {

        if (guildUtil.isAdminOrGuildAdmin(memberId, requestDto.guildId())) {
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
        if (guildUtil.isAdminOrGuildAdmin(memberId, guildId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (guildChatRoomRepository.existsByTitleAndGuildId(requestDto.title(), guildId)) {
            throw new IllegalArgumentException("이미 존재하는 채팅방 제목입니다.");
        }

        guildChatRoom.update(requestDto.title(), requestDto.description());
        guildChatRoomRepository.save(guildChatRoom);
    }

    public void deleteGuildChatRoom(Long guildChatRoomId, Long memberId) {
        GuildChatRoom guildChatRoom = guildChatRoomRepository.findById(guildChatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        Long guildId = guildChatRoom.getGuild().getId();
        if (guildUtil.isAdminOrGuildAdmin(memberId, guildId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        guildChatRoomRepository.delete(guildChatRoom);
    }

    public GuildChatRoomResponseDto getGuildChatRoomById(
            Long guildChatRoomId,
            Long memberId) {
        GuildChatRoom guildChatRoom = guildChatRoomRepository.findById(guildChatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));
        Long guildId = guildChatRoom.getGuild().getId();

        if (!guildUtil.isAdminOrGuildMember(memberId, guildId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        return GuildChatRoomResponseDto.from(guildChatRoom);
    }

    public List<GuildChatRoomResponseDto> getGuildChatRoomsByGuildId(
            Long guildId,
            Long memberId) {

        if (!guildUtil.isAdminOrGuildMember(memberId, guildId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (!guildRepository.existsById(guildId)) {
            throw new IllegalArgumentException("존재하지 않는 길드입니다.");
        }

        return guildChatRoomRepository.findAllByGuildId(guildId).stream()
                .map(GuildChatRoomResponseDto::from)
                .toList();
    }

    public List<GuildChatRoomResponseDto> getAllGuildChatRooms() {
        return guildChatRoomRepository.findAll().stream()
                .map(GuildChatRoomResponseDto::from)
                .toList();
    }
}
