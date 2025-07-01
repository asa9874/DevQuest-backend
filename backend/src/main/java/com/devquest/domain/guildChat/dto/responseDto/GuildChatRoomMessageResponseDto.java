package com.devquest.domain.guildChat.dto.responseDto;

import java.time.LocalDateTime;

import com.devquest.domain.guildChat.model.GuildChatRoomMessage;

public record GuildChatRoomMessageResponseDto(
        Long id,
        Long guildChatRoomId,
        Long senderId,
        String senderUsername,
        String content,
        LocalDateTime sentAt) {
    public static GuildChatRoomMessageResponseDto from(
            GuildChatRoomMessage guildChatRoomMessage) {
        return new GuildChatRoomMessageResponseDto(
                guildChatRoomMessage.getId(),
                guildChatRoomMessage.getGuildChatRoom().getId(),
                guildChatRoomMessage.getMember().getId(),
                guildChatRoomMessage.getMember().getName(),
                guildChatRoomMessage.getContent(),
                guildChatRoomMessage.getSentAt());
    }
}
