package io.wisoft.testermatchingplatform.handler.exception.domain;

public class InsufficientPointException extends RuntimeException {
    public InsufficientPointException() {
    }

    public InsufficientPointException(String message) {
        super(message);
    }
}
