package com.devquest.domain.guild.service;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.dto.requestDto.GuildPostCommentCreateRequestDto;
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
    private final GuildRepository guildRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final MemberRepository memberRepository;

    public void createGuildPostComment(GuildPostCommentCreateRequestDto requestDto, Long memberId) {
        GuildPost guildPost = guildPostRepository.findById(requestDto.guildPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        if (!guildMemberRepository.existsByGuildIdAndMemberId(guildPost.getGuild().getId(), memberId) && !AuthUtil.isAdmin()) {
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
    
}
