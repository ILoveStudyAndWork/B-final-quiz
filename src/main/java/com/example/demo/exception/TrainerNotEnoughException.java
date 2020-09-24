package com.example.demo.exception;



public class TrainerNotEnoughException extends Exception {
    private final String message = "讲师过少，分组失败";

    public TrainerNotEnoughException() {
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
