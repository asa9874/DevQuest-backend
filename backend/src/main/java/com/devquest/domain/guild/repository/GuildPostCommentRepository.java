package com.devquest.domain.guild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.guild.dto.responseDto.GuildPostCommentResponseDto;
import com.devquest.domain.guild.model.GuildPostComment;

public interface GuildPostCommentRepository extends JpaRepository<GuildPostComment, Long> {

    @Query("""
            SELECT new com.devquest.domain.guild.dto.responseDto.GuildPostCommentResponseDto(
                c.id,
                c.content,
                c.guildPost.id,
                c.author.id,
                c.author.name,
                c.createdAt
            )
            FROM GuildPostComment c
            WHERE c.guildPost.id = :guildPostId
            ORDER BY c.createdAt ASC
            """)
    List<GuildPostCommentResponseDto> findDtoByGuildByPostId(
            @Param("guildPostId") Long guildPostId);

    @Query("""
            SELECT new com.devquest.domain.guild.dto.responseDto.GuildPostCommentResponseDto(
                c.id,
                c.content,
                c.guildPost.id,
                c.author.id,
                c.author.name,
                c.createdAt
            )
            FROM GuildPostComment c
            WHERE c.id = :id
            """)
    Optional<GuildPostCommentResponseDto> findDtoById(
            @Param("id") Long id);

    @Query("""
            SELECT new com.devquest.domain.guild.dto.responseDto.GuildPostCommentResponseDto(
                c.id,
                c.content,
                c.guildPost.id,
                c.author.id,
                c.author.name,
                c.createdAt
            )
            FROM GuildPostComment c
            ORDER BY c.createdAt ASC
            """)
    List<GuildPostCommentResponseDto> findAllDto();

    @Modifying
    @Query("DELETE FROM GuildPostComment c WHERE c.guildPost.id = :guildId")    
    void deleteAllByGuildId(Long guildId);
}
