package io.wisoft.testermatchingplatform.handler.exception.domain;

public class MismatchPasswordException extends RuntimeException{
    public MismatchPasswordException(String password) {
        super(password);
    }
}
