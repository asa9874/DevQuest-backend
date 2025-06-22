package com.devquest.domain.guild.model;

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
public class Guild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @NotNull
    @Size(min = 5, max = 100)
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
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
