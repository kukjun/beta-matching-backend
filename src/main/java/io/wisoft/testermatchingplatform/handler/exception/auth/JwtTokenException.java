package io.wisoft.testermatchingplatform.handler.exception.auth;

public class JwtTokenException extends RuntimeException{
    public JwtTokenException(final String msg){super(msg);}
}
