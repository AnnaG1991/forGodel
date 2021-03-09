package com.godeltech.gorodetskaya.task.controllers;

import com.godeltech.gorodetskaya.task.entity.Book;
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
public class BookController {

    @Autowired
    @Qualifier("bookServiceImpl")
    private Service<Book> service;

    @GetMapping("/books")
    public Map<Integer, Book> getAllBooks() {
        return service.getAllItems();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        return service.getItemById(id);
    }

    @PostMapping("/books")
    public Object addBook(@RequestBody @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            for (FieldError fieldError : errors) {
                message.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }
            return message;
        } else {
            service.addItem(book);
            return book;
        }
    }

    @PutMapping("/books")
    public Object updateBook(@RequestBody @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<String> message = new ArrayList<>();
            for (FieldError fieldError : errors) {
                message.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }
            return message;
        } else {
            service.updateItem(book);
            return book;
        }
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable int id) {
        service.deleteItem(id);
        return "Book with id = " + id + " was deleted";
    }
}