package com.godeltech.gorodetskaya.task.exception_handling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handleNoSuchEntityException(NoSuchEntityException exception) {
        EntityIncorrectData data = new EntityIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handleInvalidFormatException(InvalidFormatException exception) {
        EntityIncorrectData data = new EntityIncorrectData();
        data.setInfo("Choose MALE, FEMALE OR UNDEFINED as gender of the author");
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handleRepeatAddingException(RepeatAddingException exception) {
        EntityIncorrectData data = new EntityIncorrectData();
        data.setInfo("Element has already been created");
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);

    }
}
