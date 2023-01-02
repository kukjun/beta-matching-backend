package io.wisoft.testermatchingplatform.handler.exception;

public class MissionDateSequenceException extends RuntimeException {
    public MissionDateSequenceException(String s) {
        super(s);
    }
    public MissionDateSequenceException() {
        super("시간 순서대로 입력을 받지 못했습니다.");
    }
}
