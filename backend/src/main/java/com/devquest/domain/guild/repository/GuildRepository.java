package com.devquest.domain.guild.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devquest.domain.guild.model.Guild;

public interface GuildRepository extends JpaRepository<Guild, Long> {
    
}
