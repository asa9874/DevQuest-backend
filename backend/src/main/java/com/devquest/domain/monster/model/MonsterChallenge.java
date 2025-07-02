package com.devquest.domain.monster.model;

import com.devquest.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private Boolean isSuccess;

    @Builder
    public MonsterChallenge(Member member, Monster monster, LocalDateTime challengedAt, Boolean isSuccess) {
        this.member = member;
        this.monster = monster;
        this.challengedAt = challengedAt;
        this.isSuccess = isSuccess;
    }
}
