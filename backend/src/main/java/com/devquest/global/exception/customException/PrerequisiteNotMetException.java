package com.devquest.global.exception.customException;

/**
 * 사전 조건이 충족되지 않았을 때 발생하는 예외
 */
public class PrerequisiteNotMetException extends RuntimeException {
    public PrerequisiteNotMetException(String message) {
        super(message);
    }

    public PrerequisiteNotMetException(String message, Throwable cause) {
        super(message, cause);
    }
}
