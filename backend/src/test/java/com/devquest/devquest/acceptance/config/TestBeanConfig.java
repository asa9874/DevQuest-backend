package com.devquest.devquest.acceptance.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.devquest.domain.guild.service.GuildService;

@TestConfiguration
public class TestBeanConfig {

    @MockBean
    private GuildService guildService;

    @Bean
    public GuildService guildService() {
        return guildService;
    }
}
