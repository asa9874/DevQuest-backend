package com.devquest.domain.guildChat.dto.requestDto;

public record GuildChatRoomCreateRequestDto(
        String title,
        String description,
        Long guildId
) {
    
}
