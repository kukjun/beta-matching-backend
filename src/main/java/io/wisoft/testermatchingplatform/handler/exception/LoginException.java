package io.wisoft.testermatchingplatform.handler.exception;

public class LoginException extends RuntimeException{
    public LoginException() {
        super("해당 Id, Password를 가지는 사용자가 없습니다.");
    }
}
