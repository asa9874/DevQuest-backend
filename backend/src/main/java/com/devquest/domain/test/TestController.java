package com.devquest.domain.test;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController implements TestApi {
    private final TestService testService;

    @GetMapping("/redistest")
    public String redistest() {
        return testService.testRedisConnection();
    }

    @GetMapping("/redis-all")
    public ResponseEntity<Map<String, String>> getAllRedis() {
        return ResponseEntity.ok(testService.getAllRedisKeyValues());
    }
}
