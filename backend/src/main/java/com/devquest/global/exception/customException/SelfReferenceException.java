package com.devquest.global.exception.customException;

/**
 * 자기 자신을 참조하는 경우 발생하는 예외
 */
public class SelfReferenceException extends RuntimeException {
    public SelfReferenceException(String message) {
        super(message);
    }

    public SelfReferenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
