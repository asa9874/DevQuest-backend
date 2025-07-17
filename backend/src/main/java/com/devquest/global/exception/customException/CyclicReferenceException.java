package com.devquest.global.exception.customException;

/**
 * 순환 참조가 발생할 때 발생하는 예외
 */
public class CyclicReferenceException extends RuntimeException {
    public CyclicReferenceException(String message) {
        super(message);
    }

    public CyclicReferenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
