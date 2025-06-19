package com.devquest.domain.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
    private final StringRedisTemplate redisTemplate;
    public String testRedisConnection() {
        try {
            redisTemplate.opsForValue().set("testKey", "testValue");
            String value = redisTemplate.opsForValue().get("testKey");
            return (value != null) ? "Redis 연결 성공: " + value : "Redis 연결 실패";
        } catch (Exception e) {
            return "Redis 연결 오류: " + e.getMessage();
        }
    }

    public Map<String, String> getAllRedisKeyValues() {
        Set<String> keys = redisTemplate.keys("*"); // 모든 키 가져오기
        if (keys == null || keys.isEmpty()) {
            return Map.of(); // 빈 맵 반환
        }

        Map<String, String> result = new HashMap<>();
        for (String key : keys) {
            String value = redisTemplate.opsForValue().get(key);
            result.put(key, value);
        }

        return result;
    }
}
