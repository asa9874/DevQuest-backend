package com.devquest.domain.guildChat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.devquest.domain.guildChat.dto.requestDto.GuildChatRoomMessageSendRequestDto;
import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto;
import com.devquest.domain.guildChat.service.GuildChatRoomMessageService;
import com.devquest.domain.guildChat.service.GuildChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class GuildChatRoomMessageWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final GuildChatRoomMessageService guildChatRoomMessageService;

    @MessageMapping("/chat/send")
    public void sendMessage(GuildChatRoomMessageSendRequestDto requestDto, SimpMessageHeaderAccessor headerAccessor) {
        Long senderId = (Long) headerAccessor.getSessionAttributes().get("userId");
        GuildChatRoomMessageResponseDto savedMessage = guildChatRoomMessageService.sendMessage(requestDto, senderId);
        messagingTemplate.convertAndSend("/topic/chat/" + requestDto.chatRoomId(), savedMessage);
    }
}
