package com.devquest.domain.guild.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.guild.model.GuildPost;

public interface GuildPostRepository extends JpaRepository<GuildPost, Long> {
}