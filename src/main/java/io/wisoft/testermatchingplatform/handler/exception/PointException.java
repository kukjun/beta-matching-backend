package io.wisoft.testermatchingplatform.handler.exception;

public class PointException extends RuntimeException{
    public PointException() {
        super("잔여 포인트가 부족합니다.");
    }
    public PointException(String s) {
        super(s);
    }
}
