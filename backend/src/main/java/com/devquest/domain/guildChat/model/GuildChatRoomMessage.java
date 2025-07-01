package com.devquest.domain.guildChat.model;

import java.time.LocalDateTime;

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
public class GuildChatRoomMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guild_chat_room_id", nullable = false)
    private GuildChatRoom guildChatRoom;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @Builder
    public GuildChatRoomMessage(GuildChatRoom guildChatRoom, Member member, String content) {
        this.guildChatRoom = guildChatRoom;
        this.member = member;
        this.content = content;
        this.sentAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
    }

}

