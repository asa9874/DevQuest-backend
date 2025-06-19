package com.devquest.domain.auth.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.devquest.domain.member.model.Member;

public class CustomOAuth2User implements OAuth2User {

    private Member member;
    private Map<String, Object> attributes;
    
    public CustomOAuth2User(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }
    
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_" + member.getRole().name());
    }
    
    @Override
    public String getName() {
        return member.getName();
    }
    
    public String getEmail() {
        return member.getEmail();
    }
    
    public Member getMember() {
        return this.member;
    }
}
