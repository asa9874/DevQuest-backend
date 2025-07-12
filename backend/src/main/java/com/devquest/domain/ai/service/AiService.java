package com.devquest.domain.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class AiService {

    private final RestTemplate restTemplate;
    
    @Value("${ai.server.url:http://ai-backend:5000}")
    private String aiServerUrl;
    
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    
    public Map<String, Object> generateQuest(String taskInput) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("task_input", taskInput);
        
        ResponseEntity response = restTemplate.postForEntity(
            aiServerUrl + "/api/quest", 
            new HttpEntity<>(requestBody, createHeaders()), 
            Map.class
        );
        
        Map<String, Object> result = (Map<String, Object>) response.getBody();
        return result != null ? result : new HashMap<>();
    }
    
    public Map<String, Object> generateQuiz(String taskInput) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("task_input", taskInput);
        
        ResponseEntity response = restTemplate.postForEntity(
            aiServerUrl + "/api/quiz", 
            new HttpEntity<>(requestBody, createHeaders()), 
            Map.class
        );
        
        Map<String, Object> result = (Map<String, Object>) response.getBody();
        return result != null ? result : new HashMap<>();
    }
    
    public List<Map<String, Object>> generateQuizBatch(String name, String description, int number) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("number", Math.min(number, 10));
        
        ResponseEntity response = restTemplate.postForEntity(
            aiServerUrl + "/api/quiz/batch", 
            new HttpEntity<>(requestBody, createHeaders()), 
            List.class
        );
        
        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();
        return result != null ? result : new ArrayList<>();
    }
    
    public List<Map<String, Object>> searchGuilds(String query) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(aiServerUrl + "/api/guild/search")
                .queryParam("query", query);
        
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
            builder.toUriString(), 
            HttpMethod.GET, 
            new HttpEntity<>(createHeaders()), 
            new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );
        
        return response.getBody() != null ? response.getBody() : new ArrayList<>();
    }
}
