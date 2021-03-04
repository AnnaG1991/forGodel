package com.godeltech.gorodetskaya.task.exception_handing;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String message) {
        super(message);
    }
}
