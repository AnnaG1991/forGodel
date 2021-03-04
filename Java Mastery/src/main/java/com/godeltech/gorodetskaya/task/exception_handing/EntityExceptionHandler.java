package com.godeltech.gorodetskaya.task.exception_handing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handleException(NoSuchEntityException exception) {
        EntityIncorrectData data = new EntityIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}