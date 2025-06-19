package com.devquest.domain.auth.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.devquest.domain.auth.model.CustomOAuth2User;
import com.devquest.domain.member.model.Member;
import com.devquest.domain.member.model.Role;
import com.devquest.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        Member member = memberRepository.findByEmail(email)
            .orElseGet(() -> {
                Member newMember = Member.builder()
                    .email(email)
                    .name("개발자"+(String) attributes.get("name")+ UUID.randomUUID().toString().substring(0, 7))
                    .password(UUID.randomUUID().toString())
                    .role(Role.USER)   
                    .provider(registrationId) // OAuth2 공급자 정보 저장
                .build();

                return memberRepository.save(newMember);
            });
        return new CustomOAuth2User(member, attributes);
    }
}