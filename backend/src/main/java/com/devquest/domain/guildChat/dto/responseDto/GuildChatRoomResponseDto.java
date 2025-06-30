package com.devquest.domain.guildChat.dto.responseDto;

import com.devquest.domain.guildChat.model.GuildChatRoom;

public record GuildChatRoomResponseDto(
        Long id,
        String title,
        String description
) {
    public static GuildChatRoomResponseDto from(GuildChatRoom guildChatRoom) {
        return new GuildChatRoomResponseDto(
                guildChatRoom.getId(),
                guildChatRoom.getTitle(),
                guildChatRoom.getDescription()
        );
    }
}
