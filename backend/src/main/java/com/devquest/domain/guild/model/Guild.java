package com.devquest.domain.guild.model;

import com.devquest.domain.member.model.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member leader;

    @Builder
    public Guild(String name, String description, Member leader) {
        this.name = name;
        this.description = description;
        this.leader = leader;
    }

    public void updateGuild(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
