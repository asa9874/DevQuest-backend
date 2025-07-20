package com.devquest.domain.guildChat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.guildChat.model.GuildChatRoom;

public interface GuildChatRoomRepository extends JpaRepository<GuildChatRoom, Long> {
    boolean existsByTitleAndGuildId(String title, Long guildId);
    List<GuildChatRoom> findAllByGuildId(Long guildId);
    void deleteAllByGuildId(Long guildId);
}
