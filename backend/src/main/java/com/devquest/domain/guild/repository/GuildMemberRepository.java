package com.devquest.domain.guild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.guild.dto.responseDto.GuildMemberResponseDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.model.GuildMember;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;

public interface GuildMemberRepository extends JpaRepository<GuildMember, Long> {
    List<GuildMember> findByGuildId(Long guildId);

    @Query("""
            SELECT  new com.devquest.domain.guild.dto.responseDto.GuildMemberResponseDto(
                gm.id,
                gm.member.id,
                gm.member.name,
                gm.status,
                gm.role,
                gm.joinedAt
            )
            FROM GuildMember gm
            WHERE gm.guild.id = :guildId
            AND (:status IS NULL OR gm.status = :status)
            AND (:role IS NULL OR gm.role = :role)
            """)
    List<GuildMemberResponseDto> findGuildMembersByGuildIdAndStatusAndRole(
            @Param("guildId") Long guildId,
            @Param("status") GuildMemberStatus status,
            @Param("role") GuildMemberRole role);

    @Query("""
            SELECT new com.devquest.domain.guild.dto.responseDto.GuildResponseDto(
                gm.guild.id,
                gm.guild.name,
                gm.guild.description,
                gm.guild.leader.name
            )
            FROM GuildMember gm
            WHERE gm.member.id = :memberId
            AND (:status IS NULL OR gm.status = :status)
            AND (:role IS NULL OR gm.role = :role)
        """)
    List<GuildResponseDto> findGuildsByMemberIdAndStatusAndRole(
            @Param("memberId") Long memberId,
            @Param("status") GuildMemberStatus status,
            @Param("role") GuildMemberRole role);

    Boolean existsByMemberIdAndStatus(Long memberId, GuildMemberStatus status);
    Boolean existsByGuildIdAndMemberId(Long guildId, Long memberId);
    Boolean existsByGuildIdAndMemberIdAndStatus(Long guildId, Long memberId, GuildMemberStatus status);
    Boolean existsByGuildIdAndMemberIdAndStatusAndRole(Long guildId, Long memberId, GuildMemberStatus status, GuildMemberRole role);
    Optional<GuildMember> findByGuildIdAndMemberId(Long guildId, Long memberId);
    void deleteAllByGuildId(Long guildId);
}
