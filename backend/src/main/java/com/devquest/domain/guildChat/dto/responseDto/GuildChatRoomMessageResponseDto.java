package com.devquest.domain.guildChat.dto.responseDto;

public record GuildChatRoomMessageResponseDto(
        Long id,
        Long guildChatRoomId,
        Long senderId,
        String senderUsername,
        String content,
        String timestamp
) {
    
}
