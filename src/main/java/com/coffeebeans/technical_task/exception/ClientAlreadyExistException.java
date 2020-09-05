package com.coffeebeans.technical_task.exception;


import lombok.Getter;

@Getter
public class ClientAlreadyExistException extends RuntimeException{

    String message;

    public ClientAlreadyExistException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
