package com.godeltech.gorodetskaya.task.exception_handling;

public class RepeatAddingException extends RuntimeException {
    public RepeatAddingException(String message) {
        super(message);
    }
}
