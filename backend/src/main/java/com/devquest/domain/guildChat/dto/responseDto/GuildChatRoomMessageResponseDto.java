package com.devquest.domain.guildChat.dto.responseDto;

import java.time.LocalDateTime;

public record GuildChatRoomMessageResponseDto(
        Long id,
        Long guildChatRoomId,
        Long senderId,
        String senderUsername,
        String content,
        LocalDateTime sentAt
) {
    
}
