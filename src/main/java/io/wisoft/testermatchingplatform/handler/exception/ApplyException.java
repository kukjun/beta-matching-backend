package io.wisoft.testermatchingplatform.handler.exception;

public class ApplyException extends RuntimeException {
    public ApplyException(String s) {
        super(s);
    }
    public ApplyException() {
        super("생생 시, 제한 인원을 초과했습니다.");
    }
}
