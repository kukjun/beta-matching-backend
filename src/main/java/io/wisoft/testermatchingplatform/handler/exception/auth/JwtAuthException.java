package io.wisoft.testermatchingplatform.handler.exception.auth;

public class JwtAuthException extends RuntimeException {
    public JwtAuthException(final String msg){super(msg);}
}
