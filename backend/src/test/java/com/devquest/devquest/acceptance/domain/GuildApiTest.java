package com.devquest.devquest.acceptance.domain;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.devquest.devquest.acceptance.config.CustomUserDetailsArgumentResolver;
import com.devquest.devquest.acceptance.config.TestExceptionHandler;
import com.devquest.domain.guild.controller.GuildController;
import com.devquest.domain.guild.dto.requestDto.GuildCreateRequestDto;
import com.devquest.domain.guild.dto.requestDto.GuildUpdateRequestDto;
import com.devquest.domain.guild.dto.responseDto.GuildResponseDto;
import com.devquest.domain.guild.service.GuildService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class GuildApiTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private GuildService guildService;

    @InjectMocks
    private GuildController guildController;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        
        mockMvc = MockMvcBuilders.standaloneSetup(guildController)
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver(),
                        new CustomUserDetailsArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .setControllerAdvice(new TestExceptionHandler())  // 테스트용 예외 처리기 추가
                .build();
    }

    @Test
    void 길드를_생성한다() throws Exception {
        // given
        GuildCreateRequestDto requestDto = new GuildCreateRequestDto("테스트 길드", "길드 설명입니다. 환영합니다.");
        
        // memberId가 null이 아닌 실제 ID 값 설정
        doNothing().when(guildService).createGuild(any(GuildCreateRequestDto.class), anyLong());

        // when & then
        mockMvc.perform(post("/api/guilds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());

        verify(guildService, times(1)).createGuild(any(GuildCreateRequestDto.class), anyLong());
    }

    @Test
    void 모든_길드를_조회한다() throws Exception {
        // given
        List<GuildResponseDto> guilds = Arrays.asList(
            new GuildResponseDto(1L, "길드1", "길드1 설명", "김개발"),
            new GuildResponseDto(2L, "길드2", "길드2 설명", "이코딩")
        );
        
        when(guildService.getAllGuilds()).thenReturn(guilds);

        // when & then
        mockMvc.perform(get("/api/guilds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("길드1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("길드2"));

        verify(guildService, times(1)).getAllGuilds();
    }

    @Test
    void 길드를_검색한다() throws Exception {
        // given
        List<GuildResponseDto> guilds = Arrays.asList(
            new GuildResponseDto(1L, "개발 길드", "개발 길드 설명", "김개발")
        );
        
        Page<GuildResponseDto> page = new PageImpl<>(guilds, PageRequest.of(0, 10), 1);
        
        when(guildService.searchGuilds(anyString(), any(Pageable.class))).thenReturn(page);

        // when & then
        mockMvc.perform(get("/api/guilds/search")
                .param("name", "개발"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("개발 길드"));

        verify(guildService, times(1)).searchGuilds(anyString(), any(Pageable.class));
    }

    @Test
    void 특정_길드를_조회한다() throws Exception {
        // given
        GuildResponseDto guild = new GuildResponseDto(1L, "테스트 길드", "길드 설명입니다.", "김개발");
        
        when(guildService.getGuildById(1L)).thenReturn(guild);

        // when & then
        mockMvc.perform(get("/api/guilds/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("테스트 길드"))
                .andExpect(jsonPath("$.description").value("길드 설명입니다."))
                .andExpect(jsonPath("$.leaderName").value("김개발"));

        verify(guildService, times(1)).getGuildById(1L);
    }

    @Test
    void 길드_정보를_수정한다() throws Exception {
        // given
        GuildUpdateRequestDto requestDto = new GuildUpdateRequestDto("수정된 길드", "수정된 길드 설명입니다.");
        GuildResponseDto responseDto = new GuildResponseDto(1L, "수정된 길드", "수정된 길드 설명입니다.", "김개발");
        
        when(guildService.updateGuild(anyLong(), any(GuildUpdateRequestDto.class))).thenReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/guilds/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("수정된 길드"))
                .andExpect(jsonPath("$.description").value("수정된 길드 설명입니다."));

        verify(guildService, times(1)).updateGuild(anyLong(), any(GuildUpdateRequestDto.class));
    }

    @Test
    void 길드를_삭제한다() throws Exception {
        // given
        doNothing().when(guildService).deleteGuild(anyLong());

        // when & then
        mockMvc.perform(delete("/api/guilds/1"))
                .andExpect(status().isNoContent());

        verify(guildService, times(1)).deleteGuild(anyLong());
    }

}
