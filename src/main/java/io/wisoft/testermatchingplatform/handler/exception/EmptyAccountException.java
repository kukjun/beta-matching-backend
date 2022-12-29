package io.wisoft.testermatchingplatform.handler.exception;

public class EmptyAccountException extends RuntimeException {
    public EmptyAccountException() {
        super("Account 가 등록되어 있지 않습니다.");
    }
}
