package com.devquest.domain.quest.model;

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
public class QuestChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuestStatus status = QuestStatus.IN_PROGRESS;

    @NotNull
    private LocalDateTime startedAt;

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
