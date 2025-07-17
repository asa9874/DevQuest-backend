package com.devquest.global.exception.customException;

/**
 * 이미 습득한 자원에 대해 다시 습득을 시도할 때 발생하는 예외
 */
public class ResourceAlreadyAcquiredException extends RuntimeException {
    public ResourceAlreadyAcquiredException(String message) {
        super(message);
    }

    public ResourceAlreadyAcquiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
