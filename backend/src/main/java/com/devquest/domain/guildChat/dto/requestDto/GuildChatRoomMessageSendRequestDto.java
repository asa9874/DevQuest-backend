package com.devquest.domain.guildChat.dto.requestDto;

public record GuildChatRoomMessageSendRequestDto(
        Long chatRoomId,
        String content,
        Long senderId
) {

}
