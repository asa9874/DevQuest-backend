package com.devquest.domain.guild.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.dto.requestDto.GuildPostCommentCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildPostCommentUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostCommentResponseDto;
import com.devquest.domain.guild.model.GuildPost;
import com.devquest.domain.guild.model.GuildPostComment;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildPostCommentRepository;
import com.devquest.domain.guild.repository.GuildPostRepository;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildPostCommentService {
    private final GuildPostCommentRepository guildPostCommentRepository;
    private final GuildPostRepository guildPostRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final MemberRepository memberRepository;

    public void createGuildPostComment(GuildPostCommentCreateRequestDto requestDto, Long memberId) {
        GuildPost guildPost = guildPostRepository.findById(requestDto.guildPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        if (!guildMemberRepository.existsByGuildIdAndMemberId(guildPost.getGuild().getId(), memberId)
                && !AuthUtil.isAdmin()) {
            throw new IllegalArgumentException("해당 길드에 가입하지 않은 회원입니다");
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        GuildPostComment guildPostComment = GuildPostComment.builder()
                .content(requestDto.content())
                .guildPost(guildPost)
                .author(member)
                .build();
        guildPostCommentRepository.save(guildPostComment);
    }

    public List<GuildPostCommentResponseDto> getGuildPostCommentsByPostId(Long postId, Long memberId) {
        GuildPost guildPost = guildPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));

        if (!guildMemberRepository.existsByGuildIdAndMemberId(guildPost.getGuild().getId(), memberId)
                && !AuthUtil.isAdmin()) {
            throw new IllegalArgumentException("해당 길드에 가입하지 않은 회원입니다");
        }

        if (!guildMemberRepository.existsByGuildIdAndMemberId(guildPost.getGuild().getId(),
                AuthUtil.getCurrentMemberId()) && !AuthUtil.isAdmin()) {
            throw new IllegalArgumentException("해당 길드에 가입하지 않은 회원입니다");
        }

        List<GuildPostCommentResponseDto> responseDtos = guildPostCommentRepository.findDtoByGuildByPostId(postId);
        return responseDtos;
    }

    public GuildPostCommentResponseDto getGuildPostCommentById(Long commentId, Long memberId) {
        GuildPostCommentResponseDto responseDto = guildPostCommentRepository.findDtoById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다"));

        if (!AuthUtil.isAdminOrEqualMember(responseDto.authorId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        return responseDto;
    }

    public List<GuildPostCommentResponseDto> getAllGuildPostComments() {
        List<GuildPostCommentResponseDto> responseDtos = guildPostCommentRepository.findAllDto();
        return responseDtos;
    }

    public void updateGuildPostComment(Long commentId, GuildPostCommentUpdateRequestDto requestDto) {
        GuildPostComment guildPostComment = guildPostCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다"));

        if (!AuthUtil.isAdminOrEqualMember(guildPostComment.getAuthor().getId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        guildPostComment.update(requestDto.content());
        guildPostCommentRepository.save(guildPostComment);
    }

    public void deleteGuildPostComment(Long commentId, Long memberId) {
        GuildPostComment guildPostComment = guildPostCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다"));

        if (!AuthUtil.isAdminOrEqualMember(guildPostComment.getAuthor().getId())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        guildPostCommentRepository.delete(guildPostComment);
    }
}
