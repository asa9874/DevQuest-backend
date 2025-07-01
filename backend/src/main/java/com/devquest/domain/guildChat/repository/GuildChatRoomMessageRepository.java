package com.devquest.domain.guildChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.guildChat.model.GuildChatRoomMessage;

public interface GuildChatRoomMessageRepository extends JpaRepository<GuildChatRoomMessage, Long> {
    
} 
