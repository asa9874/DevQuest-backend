package com.devquest.domain.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devquest.domain.test.dto.AdminSignupRequest;
import com.devquest.domain.test.service.AdminTestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin/test")
@RequiredArgsConstructor
@Tag(name = "테스트용 API", description = "개발용 테스트 데이터 관리 API (권한 불필요, 실제 서비스에서는 제외)")
@Slf4j
public class AdminTestController {
    
    private final AdminTestService adminTestService;
    
    @PostMapping("/generate-data")
    @Operation(summary = "테스트 데이터 생성", description = "개발용 테스트 데이터를 생성합니다.")
    public ResponseEntity<Void> generateTestData() {
        adminTestService.generateTestData();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @DeleteMapping("/clear-all-data")
    @Operation(summary = "모든 데이터 삭제", description = "데이터베이스의 모든 데이터를 삭제합니다 (개발용)")
    public ResponseEntity<Void> clearAllData() {
        adminTestService.clearAllData();
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/admin-signup")
    @Operation(summary = "관리자 회원가입", description = "개발용 관리자 회원가입 (조건 없음)")
    public ResponseEntity<Void> adminSignup(@Valid @RequestBody AdminSignupRequest request) {
        adminTestService.createAdminMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
