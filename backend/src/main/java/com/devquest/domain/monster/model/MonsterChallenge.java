package com.devquest.domain.monster.model;

import java.time.LocalDateTime;

import com.devquest.domain.member.model.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class MonsterChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monster_id", nullable = false)
    private Monster monster;

    @Column(nullable = false)
    private LocalDateTime challengedAt;

    private Boolean isSuccess;

    @Builder
    public MonsterChallenge(Member member, Monster monster) {
        this.member = member;
        this.monster = monster;
        this.challengedAt = LocalDateTime.now();
        this.isSuccess = null;
    }
    public void setSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
