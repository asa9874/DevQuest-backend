package com.devquest.domain.guildChat.dto.responseDto;

public record GuildChatRoomResponseDto(
        Long id,
        String title,
        String description,
        Long guildId,
        String guildName
) {
    
}
