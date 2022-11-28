package io.wisoft.testermatchingplatform.handler.exception;

public class TestDateSequenceException extends RuntimeException {
    public TestDateSequenceException(String s) {
        super(s);
    }
    public TestDateSequenceException() {
        super("시간 순서대로 입력을 받지 못했습니다.");
    }
}
