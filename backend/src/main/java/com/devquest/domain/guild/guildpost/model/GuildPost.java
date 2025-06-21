package com.devquest.domain.guild.guildpost.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.devquest.domain.guild.guild.model.Guild;
import com.devquest.domain.member.model.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuildPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    @NotNull
    @Size(min = 5, max = 500)
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    @NotNull
    private LocalDateTime createdAt;

    @Builder
    public GuildPost(String title, String content, Guild guild, Member author) {
        this.title = title;
        this.content = content;
        this.guild = guild;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }
}
