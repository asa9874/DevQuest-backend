package com.devquest.domain.guild.model;

import java.time.LocalDateTime;
import com.devquest.domain.member.model.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private GuildMemberStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GuildMemberRole role = GuildMemberRole.MEMBER;

    @NotNull
    private LocalDateTime joinedAt;

    private LocalDateTime leftAt;

    @Builder
    public GuildMember(Guild guild, Member member,
            GuildMemberStatus status, GuildMemberRole role) {
        this.guild = guild;
        this.member = member;
        this.status = status;
        this.role = role;
        this.joinedAt = LocalDateTime.now();
        this.leftAt = null;
    }

    public void leave() {
        this.status = GuildMemberStatus.LEAVED;
        this.leftAt = LocalDateTime.now();
    }

    public void resign() {
        this.status = GuildMemberStatus.ACTIVE;
        this.joinedAt = LocalDateTime.now();
        this.leftAt = null;
    }

    public void ban() {
        this.status = GuildMemberStatus.BANNED;
        this.leftAt = LocalDateTime.now();
    }

    public void changeRole(GuildMemberRole newRole) {
        this.role = newRole;
    }

    public void changeStatus(GuildMemberStatus newStatus) {
        this.status = newStatus;
    }

}
