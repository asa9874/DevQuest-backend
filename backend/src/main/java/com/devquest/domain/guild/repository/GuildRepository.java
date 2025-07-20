package com.devquest.domain.guild.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devquest.domain.guild.model.Guild;

public interface GuildRepository extends JpaRepository<Guild, Long> {
    boolean existsByName(String name);

    @Query("SELECT g FROM Guild g WHERE (:name IS NULL OR :name = '' OR g.name LIKE %:name%)")
    Page<Guild> findByNameContaining(
            @Param("name") String name,
            Pageable pageable);

}
