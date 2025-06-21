package com.devquest.domain.guild.model;

import java.time.LocalDateTime;

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
public class GuildPostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "guild_post_id")
    private GuildPost guildPost;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    private LocalDateTime createdAt;

    @Builder
    public GuildPostComment(String content, GuildPost guildPost, Member author) {
        this.content = content;
        this.guildPost = guildPost;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }
}
