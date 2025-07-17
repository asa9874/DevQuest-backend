package com.devquest.domain.guild.util;

import org.springframework.stereotype.Component;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.model.GuildMember;
import com.devquest.domain.guild.model.GuildMemberRole;
import com.devquest.domain.guild.model.GuildMemberStatus;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildPostCommentRepository;
import com.devquest.domain.guild.repository.GuildPostRepository;
import com.devquest.domain.guildChat.repository.GuildChatRoomMessageRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GuildValidator {

    private final GuildPostCommentRepository guildPostCommentRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final GuildChatRoomMessageRepository guildChatRoomMessageRepository;
    private final GuildPostRepository guildPostRepository;

    /*
     * 길드 관리자인지 확인
     * 길드장이거나, 관리자인지 확인
     */
    public boolean isGuildAdmin(Long memberId, Long guildId) {
        return AuthUtil.isAdmin()
                ||
                guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                        guildId, memberId, GuildMemberStatus.ACTIVE, GuildMemberRole.ADMIN)
                ||
                guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                        guildId, memberId, GuildMemberStatus.ACTIVE, GuildMemberRole.OWNER);
    }

    /*
     * 길드장이 맞는지 확인
     */
    public boolean isGuildOwner(Long memberId, Long guildId) {
        return AuthUtil.isAdmin()
                ||
                guildMemberRepository.existsByGuildIdAndMemberIdAndStatusAndRole(
                        guildId, memberId, GuildMemberStatus.ACTIVE, GuildMemberRole.OWNER);
    }

    /*
     * 길드 멤버인지 확인
     */
    public boolean isGuildMember(Long memberId, Long guildId) {
        return AuthUtil.isAdmin()
                ||
                guildMemberRepository.existsByGuildIdAndMemberIdAndStatus(
                        guildId, memberId, GuildMemberStatus.ACTIVE);
    }

    /*
     * 메시지 작성자인지 확인
     */
    public boolean isMessageSender(Long memberId, Long messageId) {
        return AuthUtil.isAdmin()
                ||
                guildChatRoomMessageRepository.existsByIdAndMemberId(messageId, memberId);
    }

    /*
     * 길드 멤버가 탈퇴한 상태인지 확인
     */
    public boolean isLeavedGuildMember(Long memberId, Long guildId) {
        return guildMemberRepository.existsByGuildIdAndMemberIdAndStatus(
                guildId, memberId, GuildMemberStatus.LEAVED);
    }

    /*
     * 길드를 탈퇴할 수 있는지 확인
     * 길드 멤버여야 하고, 길드장이 아니어야 함
     */
    public boolean canLeaveGuild(Long memberId, Long guildId) {
        return isGuildMember(memberId, guildId) &&
                !isGuildOwner(memberId, guildId);
    }

    /*
     * 길드 멤버의 역할을 변경할 수 있는지 확인
     * - 이미 해당 역할이거나, 활동 중인 멤버가 아니거나, 길드장이거나, 길드장으로 변경하려는 경우 -> 변경 불가
     * - 요청자가 길드 관리자가 아니면 변경 불가
     * - ADMIN 역할을 다루는 경우 -> 요청자는 반드시 OWNER여야 함
     */
    public boolean canChangeGuildMemberRole(GuildMember guildMember, Long guildId, Long requestMemberId,
            GuildMemberRole role) {
        if (AuthUtil.isAdmin()) {
            return true;
        }

        if (guildMember.getRole() == role || // 이미 해당 역할이거나
                guildMember.getStatus() != GuildMemberStatus.ACTIVE || // 활동 중인 멤버가 아니거나
                guildMember.getRole() == GuildMemberRole.OWNER || // 대상이 길드장이거나
                role == GuildMemberRole.OWNER) { // 길드장으로 변경하려는 경우
            return false;
        }

        if (!isGuildAdmin(requestMemberId, guildId)) {
            return false;
        }

        // 대상이 ADMIN이거나, ADMIN으로 변경하려는 경우 -> 요청자는 반드시 OWNER여야 함
        boolean isHandlingAdminRole = guildMember.getRole() == GuildMemberRole.ADMIN || role == GuildMemberRole.ADMIN;
        if (isHandlingAdminRole && !isGuildOwner(requestMemberId, guildId)) {
            return false;
        }

        return true;
    }

    /*
     * 길드 멤버를 밴할 수 있는지 확인
     * - 길드장이거나, 어드민인 경우 -> 밴 불가
     * - 이미 밴된 멤버는 밴할 수 없음
     */
    public boolean canBanGuildMember(GuildMember guildMember, Long guildId, Long requestMemberId) {
        if (guildMember.getRole() == GuildMemberRole.OWNER || guildMember.getRole() == GuildMemberRole.ADMIN) {
            return false; // 길드장과 어드민은 밴할 수 없음
        }
        if (guildMember.getStatus() == GuildMemberStatus.BANNED) {
            return false; // 이미 밴된 길드 멤버는 밴할 수 없음
        }
        return true;
    }

    /*
     * 길드 멤버가 밴된 상태인지 확인
     */
    public boolean isBannedGuildMember(Long memberId, Long guildId) {
        return guildMemberRepository.existsByGuildIdAndMemberIdAndStatus(
                guildId, memberId, GuildMemberStatus.BANNED);
    }

    /*
     * 길드 게시글 작성자인지 확인
     */
    public boolean isGuildPostAuthor(Long postId, Long memberId) {
        return guildPostRepository.existsByIdAndAuthor_Id(postId, memberId);
    }

    /*
     * 길드 게시글 댓글 작성자인지 확인
     */
    public boolean isGuildPostCommentAuthor(Long commentId, Long memberId) {
        return guildPostCommentRepository.existsByIdAndAuthor_Id(commentId, memberId);
    }

}
