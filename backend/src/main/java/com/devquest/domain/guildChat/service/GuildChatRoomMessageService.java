package com.devquest.domain.guildChat.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.guild.util.GuildValidator;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageCreateRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageSendRequestDto;
import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageUpdateRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto;
import com.devquest.domain.guildChat.model.GuildChatRoom;
import com.devquest.domain.guildChat.model.GuildChatRoomMessage;
import com.devquest.domain.guildChat.repository.GuildChatRoomMessageRepository;
import com.devquest.domain.guildChat.repository.GuildChatRoomRepository;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildChatRoomMessageService {
    private final GuildChatRoomMessageRepository guildChatRoomMessageRepository;
    private final GuildChatRoomRepository guildChatRoomRepository;
    private final MemberRepository memberRepository;
    private final GuildValidator guildUtil;

    public List<GuildChatRoomMessageResponseDto> getAllMessages() {
        return guildChatRoomMessageRepository.findAllDto();
    }

    public GuildChatRoomMessageResponseDto getMessageById(
            Long messageId) {

        GuildChatRoomMessageResponseDto responseDto = guildChatRoomMessageRepository.findDtoById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 메시지입니다."));
        return responseDto;
    }

    public List<GuildChatRoomMessageResponseDto> getMessagesByGuildChatRoomId(
            Long guildChatRoomId,
            Long memberId) {
        if (!guildUtil.isGuildMember(memberId, guildChatRoomId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        List<GuildChatRoomMessageResponseDto> responseDtos = guildChatRoomMessageRepository
                .findDtoByGuildChatRoomId(guildChatRoomId);

        return responseDtos;
    }

    public void createMessage(
            Long guildChatRoomId,
            GuildChatRoomMessageCreateRequestDto requestDto,
            Long memberId) {
        if (!guildUtil.isGuildMember(memberId, guildChatRoomId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        GuildChatRoom guildChatRoom = guildChatRoomRepository.findById(guildChatRoomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드 채팅방입니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        GuildChatRoomMessage message = GuildChatRoomMessage.builder()
                .guildChatRoom(guildChatRoom)
                .member(member)
                .content(requestDto.content())
                .build();

        guildChatRoomMessageRepository.save(message);
    }

    public GuildChatRoomMessageResponseDto sendMessage(
            GuildChatRoomMessageSendRequestDto requestDto,
            Long senderId) {
        if (senderId != requestDto.senderId()) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        GuildChatRoom guildChatRoom = guildChatRoomRepository.findById(requestDto.chatRoomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드 채팅방입니다."));

        Member member = memberRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        GuildChatRoomMessage message = GuildChatRoomMessage.builder()
                .guildChatRoom(guildChatRoom)
                .member(member)
                .content(requestDto.content())
                .build();

        guildChatRoomMessageRepository.save(message);

        return GuildChatRoomMessageResponseDto.from(message);
    }

    public void updateMessage(
            Long messageId,
            GuildChatRoomMessageUpdateRequestDto requestDto,
            Long memberId) {

        if (!guildUtil.isMessageSender(memberId, messageId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        GuildChatRoomMessage message = guildChatRoomMessageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 메시지입니다."));

        message.updateContent(requestDto.content());

        guildChatRoomMessageRepository.save(message);
    }

    public void deleteMessage(
            Long messageId,
            Long memberId) {

        if (!guildUtil.isMessageSender(memberId, messageId)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        GuildChatRoomMessage message = guildChatRoomMessageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 메시지입니다."));

        guildChatRoomMessageRepository.delete(message);
    }
}
