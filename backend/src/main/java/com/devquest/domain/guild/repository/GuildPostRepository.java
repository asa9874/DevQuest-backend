package com.devquest.domain.guild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto;
import com.devquest.domain.guild.model.GuildPost;

public interface GuildPostRepository extends JpaRepository<GuildPost, Long> {

    @Query("""
            select new com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto(
                gp.id,
                gp.title,
                gp.content,
                gp.guild.id,
                gp.guild.name,
                gp.author.id,
                gp.author.name,
                gp.createdAt
            )
            from GuildPost gp
            where gp.id = :id
            """)
    Optional<GuildPostResponseDto> findDtoById(
            @Param("id") Long id);

    @Query("""
            select new com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto(
                gp.id,
                gp.title,
                gp.content,
                gp.guild.id,
                gp.guild.name,
                gp.author.id,
                gp.author.name,
                gp.createdAt
            )
            from GuildPost gp
            where gp.guild.id = :guildId
            order by gp.createdAt desc
            """)
    List<GuildPostResponseDto> findDtoByGuildIdOrderByCreatedAtDesc(
            @Param("guildId") Long guildId);


    @Query("""
            select new com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto(
                gp.id,
                gp.title,
                gp.content,
                gp.guild.id,
                gp.guild.name,
                gp.author.id,
                gp.author.name,
                gp.createdAt
            )
            from GuildPost gp
            """)
    List<GuildPostResponseDto> findAllDto();
 
    void deleteAllByGuildId(Long guildId);

    boolean existsByIdAndMemberId(Long postId, Long memberId);
}