package io.wisoft.testermatchingplatform.handler.exception;

public class CashException extends RuntimeException {

    public CashException() {
        super("현금 잔액이 부족합니다.");
    }
    public CashException(String s) {
        super(s);
    }
}
