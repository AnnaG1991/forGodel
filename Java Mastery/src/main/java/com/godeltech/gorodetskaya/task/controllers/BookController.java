package com.godeltech.gorodetskaya.task.controllers;

import com.godeltech.gorodetskaya.task.entity.Book;
import com.godeltech.gorodetskaya.task.exception_handing.EntityIncorrectData;
import com.godeltech.gorodetskaya.task.exception_handing.NoSuchEntityException;
import com.godeltech.gorodetskaya.task.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return book;
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody Book book){
        bookService.updateBook(book);
        return book;
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
        return "Book with id = " + id + " was deleted";
    }
}

