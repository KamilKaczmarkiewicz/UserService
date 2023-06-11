package com.kams.UserService.user.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(value = {WrongUpdateUserIdException.class})
    public ResponseEntity<Object> handleWrongUpdateUserIdException(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
    }
}
