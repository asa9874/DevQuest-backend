package com.devquest.domain.guild.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.guild.model.GuildMember;

public interface GuildMemberRepository extends JpaRepository<GuildMember, Long> {
}
