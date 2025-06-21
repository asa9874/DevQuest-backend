package com.devquest.domain.quest.model;

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
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    @NotNull
    @Size(min = 5, max = 500)
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member creater;

    @NotNull
    private LocalDateTime createdAt;

    @Builder
    public Quest(String title, String description, Member creater) {
        this.title = title;
        this.description = description;
        this.creater = creater;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
