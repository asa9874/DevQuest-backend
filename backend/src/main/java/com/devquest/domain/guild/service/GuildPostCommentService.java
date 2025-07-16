package com.devquest.domain.guild.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
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
import com.devquest.domain.guild.util.GuildValidator;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildPostCommentService {
    private final GuildPostCommentRepository guildPostCommentRepository;
    private final GuildPostRepository guildPostRepository;
    private final MemberRepository memberRepository;
    private final GuildValidator guildValidator;

    public void createGuildPostComment(GuildPostCommentCreateRequestDto requestDto, Long memberId) {
        GuildPost guildPost = guildPostRepository.findById(requestDto.guildPostId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다"));

        if (!guildValidator.isGuildMember(memberId, guildPost.getGuild().getId())) {
            throw new IllegalArgumentException("해당 길드에 가입하지 않은 회원입니다");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다"));

        GuildPostComment guildPostComment = GuildPostComment.builder()
                .content(requestDto.content())
                .guildPost(guildPost)
                .author(member)
                .build();

        guildPostCommentRepository.save(guildPostComment);
    }

    public List<GuildPostCommentResponseDto> getGuildPostCommentsByPostId(Long postId, Long memberId) {
        GuildPost guildPost = guildPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다"));

        if (!guildValidator.isGuildMember(memberId, guildPost.getGuild().getId())) {
            throw new AccessDeniedException("해당 길드에 가입하지 않은 회원입니다");
        }

        List<GuildPostCommentResponseDto> responseDtos = guildPostCommentRepository.findDtoByGuildByPostId(postId);
        return responseDtos;
    }

    public GuildPostCommentResponseDto getGuildPostCommentById(Long commentId, Long memberId) {
        GuildPostCommentResponseDto responseDto = guildPostCommentRepository.findDtoById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다"));

        if (!AuthUtil.isAdminOrEqualMember(responseDto.authorId())) {
            throw new AccessDeniedException("권한이 없습니다");
        }

        return responseDto;
    }

    public List<GuildPostCommentResponseDto> getAllGuildPostComments() {
        List<GuildPostCommentResponseDto> responseDtos = guildPostCommentRepository.findAllDto();
        return responseDtos;
    }

    public void updateGuildPostComment(Long commentId, GuildPostCommentUpdateRequestDto requestDto) {
        GuildPostComment guildPostComment = guildPostCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다"));

        if (!guildValidator.isGuildPostCommentAuthor(commentId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("권한이 없습니다");
        }

        guildPostComment.update(requestDto.content());
        guildPostCommentRepository.save(guildPostComment);
    }

    public void deleteGuildPostComment(Long commentId, Long memberId) {
        GuildPostComment guildPostComment = guildPostCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다"));

        if (!guildValidator.isGuildPostCommentAuthor(commentId, memberId)) {
            throw new AccessDeniedException("권한이 없습니다");
        }

        guildPostCommentRepository.delete(guildPostComment);
    }
}
