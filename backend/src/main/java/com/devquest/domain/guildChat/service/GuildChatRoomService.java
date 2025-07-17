package com.devquest.domain.guildChat.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.guild.util.GuildValidator;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomResponseDto;
import com.devquest.domain.guildChat.model.GuildChatRoom;
import com.devquest.domain.guildChat.repository.GuildChatRoomRepository;
import com.devquest.global.exception.customException.DuplicateDataException;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildChatRoomService {
    private final GuildChatRoomRepository guildChatRoomRepository;
    private final GuildRepository guildRepository;
    private final GuildValidator guildValidator;

    public void createGuildChatRoom(
            GuildChatRoomCreateRequestDto requestDto,
            Long memberId) {
        if (!guildValidator.isGuildAdmin(memberId, requestDto.guildId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        Guild guild = guildRepository.findById(requestDto.guildId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드입니다."));

        if (guildChatRoomRepository.existsByTitleAndGuildId(requestDto.title(), requestDto.guildId())) {
            throw new DuplicateDataException("이미 존재하는 채팅방 제목입니다.");
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
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다."));

        Long guildId = guildChatRoom.getGuild().getId();

        if (!guildValidator.isGuildAdmin(memberId, guildId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (guildChatRoomRepository.existsByTitleAndGuildId(requestDto.title(), guildId)) {
            throw new IllegalArgumentException("이미 존재하는 채팅방 제목입니다.");
        }

        guildChatRoom.update(requestDto.title(), requestDto.description());
        guildChatRoomRepository.save(guildChatRoom);
    }

    public void deleteGuildChatRoom(Long guildChatRoomId, Long memberId) {
        GuildChatRoom guildChatRoom = guildChatRoomRepository.findById(guildChatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다."));

        Long guildId = guildChatRoom.getGuild().getId();

        if (guildValidator.isGuildAdmin(memberId, guildId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        guildChatRoomRepository.delete(guildChatRoom);
    }

    public GuildChatRoomResponseDto getGuildChatRoomById(
            Long guildChatRoomId,
            Long memberId) {
        GuildChatRoom guildChatRoom = guildChatRoomRepository.findById(guildChatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅방입니다."));

        Long guildId = guildChatRoom.getGuild().getId();

        if (!guildValidator.isGuildMember(memberId, guildId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        return GuildChatRoomResponseDto.from(guildChatRoom);
    }

    public List<GuildChatRoomResponseDto> getGuildChatRoomsByGuildId(
            Long guildId,
            Long memberId) {

        if (!guildValidator.isGuildMember(memberId, guildId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (!guildRepository.existsById(guildId)) {
            throw new EntityNotFoundException("존재하지 않는 길드입니다.");
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
