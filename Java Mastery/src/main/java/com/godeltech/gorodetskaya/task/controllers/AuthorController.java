package com.godeltech.gorodetskaya.task.controllers;

import com.godeltech.gorodetskaya.task.entity.Author;
import com.godeltech.gorodetskaya.task.services.api.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class AuthorController {

    @Autowired
    @Qualifier("authorServiceImpl")
    private Service<Author> service;

    @GetMapping("/authors")
    public Map<Integer, Author> getAllAuthors() {
        return service.getAllItems();
    }

    @GetMapping("/authors/{id}")
    public Author getAuthorById(@PathVariable int id) {
        return service.getItemById(id);
    }

    @PostMapping("/authors")
    public Object addAuthor(@RequestBody @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            for (FieldError fieldError : errors) {
                message.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }
            return message;
        } else {
            service.addItem(author);
            return author;
        }
    }

    @PutMapping("/authors")
    public Object updateAuthor(@RequestBody @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            for (FieldError fieldError : errors) {
                message.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }
            return message;
        } else {
            service.updateItem(author);
            return author;
        }
    }

    @DeleteMapping("/authors/{id}")
    public String deleteAuthor(@PathVariable int id) {
        service.deleteItem(id);
        return "Author with id = " + id + " and his books were deleted";
    }
}