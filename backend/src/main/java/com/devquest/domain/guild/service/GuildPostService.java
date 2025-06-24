package com.devquest.domain.guild.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devquest.domain.auth.util.AuthUtil;
import com.devquest.domain.guild.dto.requestDto.GuildPostCreateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildPostResponseDto;
import com.devquest.domain.guild.model.Guild;
import com.devquest.domain.guild.model.GuildPost;
import com.devquest.domain.guild.repository.GuildMemberRepository;
import com.devquest.domain.guild.repository.GuildPostRepository;
import com.devquest.domain.guild.repository.GuildRepository;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuildPostService {
    private final GuildPostRepository guildPostRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final GuildRepository guildRepository;
    private final MemberRepository memberRepository;

    public void createGuildPost(
            GuildPostCreateRequestDto requestDto,
            Long memberId) {

        if (!AuthUtil.isAdminOrEqualMember(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (!guildMemberRepository.existsByGuildIdAndMemberId(requestDto.guildId(), memberId)) {
            throw new IllegalArgumentException("해당 길드에 가입되어 있지 않습니다.");
        }

        Guild guild = guildRepository.findById(requestDto.guildId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 길드입니다."));
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!AuthUtil.isAdmin() && guildMemberRepository.existsByGuildIdAndMemberId(guildPost.getGuild().getId(),
                AuthUtil.getCurrentMemberId())) {
            throw new IllegalArgumentException("해당 길드에 가입되어 있지 않습니다.");
        }

        GuildPostResponseDto guildPostResponseDto = guildPostRepository.findDtoById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return guildPostResponseDto;
    }

    public List<GuildPostResponseDto> getGuildPostsByGuildId(Long guildId) {
        if (!AuthUtil.isAdmin() && guildMemberRepository.existsByGuildIdAndMemberId(guildId,
                AuthUtil.getCurrentMemberId())) {
            throw new IllegalArgumentException("해당 길드에 가입되어 있지 않습니다.");
        }

        if (!guildRepository.existsById(guildId)) {
            throw new IllegalArgumentException("존재하지 않는 길드입니다.");
        }
        return guildPostRepository.findDtoByGuildIdOrderByCreatedAtDesc(guildId);
    }

    public List<GuildPostResponseDto> getAllGuildPosts() {
        List<GuildPostResponseDto> responseDtos = guildPostRepository.findAllDto();
        return responseDtos;

    }

}
