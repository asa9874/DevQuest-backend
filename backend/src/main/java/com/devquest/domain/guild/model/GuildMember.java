package com.devquest.domain.guild.model;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import com.devquest.domain.member.model.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuildMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private GuildMemberStatus status;

    @NotNull
    private LocalDateTime joinedAt;

    @Builder
    public GuildMember(Guild guild, Member member, GuildMemberStatus status) {
        this.guild = guild;
        this.member = member;
        this.status = status;
        this.joinedAt = LocalDateTime.now();
    }
}
