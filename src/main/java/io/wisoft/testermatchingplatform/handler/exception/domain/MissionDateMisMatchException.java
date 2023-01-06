package io.wisoft.testermatchingplatform.handler.exception.domain;

public class MissionDateMisMatchException extends RuntimeException {
    public MissionDateMisMatchException(String s) {
        super(s);
    }
    public MissionDateMisMatchException() {
        super("시간 순서대로 입력을 받지 못했습니다.");
    }
}
