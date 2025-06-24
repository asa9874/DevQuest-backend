package com.devquest.domain.test;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "테스트 API", description = "테스트/진단/개발용 API")
public interface TestApi {
    @Operation(summary = "Redis 연결 테스트", description = "Redis 연결이 정상적으로 동작하는지 테스트합니다.")
    String redistest();

    @Operation(summary = "Redis 전체 Key-Value 조회", description = "Redis에 저장된 모든 Key-Value를 조회합니다.")
    ResponseEntity<Map<String, String>> getAllRedis();
}
