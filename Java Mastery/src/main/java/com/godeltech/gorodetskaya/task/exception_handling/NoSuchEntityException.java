package com.godeltech.gorodetskaya.task.exception_handling;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String message) {
        super(message);
    }
}