package io.wisoft.testermatchingplatform.handler.exception.domain;

public class MissMatchPasswordException extends RuntimeException{
    public MissMatchPasswordException(String password) {
        super(password);
    }
}
