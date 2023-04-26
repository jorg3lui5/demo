package com.test.demo.error;

import com.test.demo.server.models.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorResolverHandler {

    @ExceptionHandler(value = FailureException.class)
    public ResponseEntity<Error> ExceptionHandler(FailureException ex){
        return new ResponseEntity<>(ex.getFailure(), ex.getStatus());
    }
}