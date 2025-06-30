package com.devquest.domain.guildChat.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GuildChatRoomCreateRequestDto(
        @NotNull(message = "제목은 필수 입력값입니다.")
        @Size(min = 1, max = 20, message = "제목은 1자 이상 20자 이하로 입력해주세요.")
        String title,
        
        @NotNull(message = "설명은 필수 입력값입니다.")
        @Size(min = 1, max = 100, message = "설명은 1자 이상 100자 이하로 입력해주세요.")
        String description,
        
        @NotNull(message = "길드 ID는 필수 입력값입니다.")
        Long guildId
) {
    
}
