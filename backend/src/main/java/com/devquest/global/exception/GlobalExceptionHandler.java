package com.devquest.global.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devquest.global.exception.customException.CyclicReferenceException;
import com.devquest.global.exception.customException.DuplicateDataException;
import com.devquest.global.exception.customException.PrerequisiteNotMetException;
import com.devquest.global.exception.customException.ResourceAlreadyAcquiredException;
import com.devquest.global.exception.customException.SelfReferenceException;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;

@Hidden
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedError(Exception ex) {
        String apiResponse = "예상치 못한 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        String apiResponse = "JWT 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        String apiResponse = "접근 거부: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        String apiResponse = "엔티티 찾기 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<String> handleDuplicateDataException(DuplicateDataException ex) {
        String apiResponse = "중복 데이터 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        String apiResponse = "잘못된 인자 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    //DTO 검증 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(msg);
    }

    @ExceptionHandler(SelfReferenceException.class)
    public ResponseEntity<String> handleSelfReferenceException(SelfReferenceException ex) {
        String apiResponse = "자기 참조 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(PrerequisiteNotMetException.class)
    public ResponseEntity<String> handlePrerequisiteNotMetException(PrerequisiteNotMetException ex) {
        String apiResponse = "선행 조건 미충족 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(apiResponse);
    }

    @ExceptionHandler(ResourceAlreadyAcquiredException.class)
    public ResponseEntity<String> handleResourceAlreadyAcquiredException(ResourceAlreadyAcquiredException ex) {
        String apiResponse = "이미 획득한 자원 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(CyclicReferenceException.class)
    public ResponseEntity<String> handleCyclicReferenceException(CyclicReferenceException ex) {
        String apiResponse = "순환 참조 오류: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
