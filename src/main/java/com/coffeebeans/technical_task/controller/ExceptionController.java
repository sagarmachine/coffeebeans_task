package com.coffeebeans.technical_task.controller;


import com.coffeebeans.technical_task.exception.AuthenticationException;
import com.coffeebeans.technical_task.exception.ClientAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ClientAlreadyExistException.class)
    public ResponseEntity<?> clientAlreadyExistExceptionHandler(ClientAlreadyExistException ex){
        return    new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public  ResponseEntity<?> authenticationExceptionHandler(AuthenticationException ex){
        return    new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
