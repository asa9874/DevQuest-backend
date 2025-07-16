package com.devquest.domain.guildChat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto;
import com.devquest.domain.guildChat.model.GuildChatRoomMessage;

public interface GuildChatRoomMessageRepository extends JpaRepository<GuildChatRoomMessage, Long> {
    boolean existsByIdAndMemberId(Long id, Long memberId);

    @Query("""
            SELECT new com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto(
                m.id,
                m.guildChatRoom.id,
                m.member.id,
                m.member.name,
                m.content,
                m.sentAt
            )
            FROM GuildChatRoomMessage m
            WHERE m.guildChatRoom.id = :guildChatRoomId
            ORDER BY m.sentAt ASC
            """)
    List<GuildChatRoomMessageResponseDto> findDtoByGuildChatRoomId(
            @Param("guildChatRoomId") Long guildChatRoomId);

    @Query("""
            SELECT new com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto(
                m.id,
                m.guildChatRoom.id,
                m.member.id,
                m.member.name,
                m.content,
                m.sentAt
            )
            FROM GuildChatRoomMessage m
            ORDER BY m.sentAt ASC
            """)
    List<GuildChatRoomMessageResponseDto> findAllDto();

    @Query("""
            SELECT new com.devquest.domain.guildChat.dto.responseDto.GuildChatRoomMessageResponseDto(
                m.id,
                m.guildChatRoom.id,
                m.member.id,
                m.member.name,
                m.content,
                m.sentAt
            )
            FROM GuildChatRoomMessage m
            WHERE m.id = :id
            """)
    Optional<GuildChatRoomMessageResponseDto> findDtoById(
            @Param("id") Long id);

    @Modifying
    @Query("DELETE FROM GuildChatRoomMessage m WHERE m.guildChatRoom.id = :guildId")
    void deleteAllByGuildId(Long guildId);
}
