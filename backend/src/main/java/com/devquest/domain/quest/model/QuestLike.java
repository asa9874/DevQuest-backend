package com.devquest.domain.quest.model;

import com.devquest.domain.member.model.Member;

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

//이건 아마 스케쥴링으로 관리할거같음 (db접근 최소화)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public QuestLike(Quest quest, Member member) {
        this.quest = quest;
        this.member = member;
    }
}
