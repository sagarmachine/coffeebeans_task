package com.coffeebeans.technical_task.exception;

public class AuthenticationException  extends RuntimeException{

    String message;

    public AuthenticationException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
