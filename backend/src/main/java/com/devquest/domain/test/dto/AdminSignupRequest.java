package com.devquest.domain.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "관리자 회원가입 요청")
public class AdminSignupRequest {
    
    @NotBlank(message = "이름은 필수입니다")
    @Schema(description = "관리자 이름", example = "관리자")
    private String name;
    
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    @Schema(description = "관리자 이메일", example = "admin@test.com")
    private String email;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Schema(description = "관리자 비밀번호", example = "admin123")
    private String password;
}
