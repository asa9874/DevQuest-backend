package com.devquest.domain.guild.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.dto.requestDto.GuildPostCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildPostUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto;
import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.model.GuildPost;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildPostCommentRepository;
import com.devquest.domain.guild.repository.GuildPostRepository;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.guild.util.GuildValidator;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildPostService {
    private final GuildPostRepository guildPostRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final GuildRepository guildRepository;
    private final MemberRepository memberRepository;
    private final GuildValidator guildValidator;
    private final GuildPostCommentRepository guildPostCommentRepository;

    public void createGuildPost(GuildPostCreateRequestDto requestDto,
            Long memberId) {

        if (!guildValidator.isGuildMember(memberId, requestDto.guildId())) {
            throw new AccessDeniedException("해당 길드에 가입되어 있지 않습니다.");
        }

        Guild guild = guildRepository.findById(requestDto.guildId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 길드입니다."));
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        GuildPost guildPost = GuildPost.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .guild(guild)
                .author(author)
                .build();
        guildPostRepository.save(guildPost);
    }

    public GuildPostResponseDto getGuildPost(Long postId) {
        GuildPost guildPost = guildPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        if (!guildValidator.isGuildMember(AuthUtil.getCurrentMemberId(), guildPost.getGuild().getId())) {
            throw new AccessDeniedException("해당 길드에 가입되어 있지 않습니다.");
        }

        GuildPostResponseDto guildPostResponseDto = guildPostRepository.findDtoById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        return guildPostResponseDto;
    }

    public List<GuildPostResponseDto> getGuildPostsByGuildId(Long guildId) {
        if (!guildValidator.isGuildMember(AuthUtil.getCurrentMemberId(), guildId)) {
            throw new AccessDeniedException("해당 길드에 가입되어 있지 않습니다.");
        }

        if (!guildRepository.existsById(guildId)) {
            throw new EntityNotFoundException("존재하지 않는 길드입니다.");
        }

        return guildPostRepository.findDtoByGuildIdOrderByCreatedAtDesc(guildId);
    }

    public List<GuildPostResponseDto> getAllGuildPosts() {
        List<GuildPostResponseDto> responseDtos = guildPostRepository.findAllDto();
        return responseDtos;
    }

    public void updateGuildPost(Long postId,
            GuildPostUpdateRequestDto requestDto) {
        GuildPost guildPost = guildPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        if (!guildValidator.isGuildPostAuthor(postId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("해당 게시글의 작성자가 아닙니다.");
        }

        guildPost.update(requestDto.title(), requestDto.content());
        guildPostRepository.save(guildPost);
    }

    public void deleteGuildPost(Long postId) {
        GuildPost guildPost = guildPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        if (!guildValidator.isGuildPostAuthor(postId, AuthUtil.getCurrentMemberId())) {
            throw new AccessDeniedException("해당 게시글의 작성자가 아닙니다.");
        }
        guildPostCommentRepository.deleteByGuildPostId(postId);
        guildPostRepository.delete(guildPost);
    }

}
