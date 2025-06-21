package com.devquest.domain.guild.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.guild.model.GuildPostComment;

public interface GuildPostCommentRepository extends JpaRepository<GuildPostComment, Long> {
}
