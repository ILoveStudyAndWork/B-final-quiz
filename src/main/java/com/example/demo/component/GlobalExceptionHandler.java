package com.example.demo.component;

import com.example.demo.exception.TrainerNotEnoughException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TrainerNotEnoughException.class)
    public ResponseEntity<String> handle(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
