package com.devquest.domain.quest.model;

import java.time.LocalDateTime;

import com.devquest.domain.member.model.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class QuestChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private QuestStatus status = QuestStatus.IN_PROGRESS;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = true)
    private LocalDateTime endAt;

    @Builder
    public QuestChallenge(Quest quest, Member member) {
        this.quest = quest;
        this.member = member;
        this.status = QuestStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
        this.endAt = null;
    }

    public void complete() {
        this.status = QuestStatus.COMPLETED;
        this.endAt = LocalDateTime.now();
    }

    public void fail() {
        this.status = QuestStatus.FAILED;
        this.endAt = LocalDateTime.now();
    }

    public boolean isInProgress() {
        return this.status == QuestStatus.IN_PROGRESS;
    }

    public boolean isCompleted() {
        return this.status == QuestStatus.COMPLETED;
    }

    public boolean isFailed() {
        return this.status == QuestStatus.FAILED;
    }
}
