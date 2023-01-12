package io.wisoft.testermatchingplatform.handler.exception.domain;

public class MissionDateMismatchException extends RuntimeException {
    public MissionDateMismatchException(String s) {
        super(s);
    }
    public MissionDateMismatchException() {
        super("시간 순서대로 입력을 받지 못했습니다.");
    }
}
