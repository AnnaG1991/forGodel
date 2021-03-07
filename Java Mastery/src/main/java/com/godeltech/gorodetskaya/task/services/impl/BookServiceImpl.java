package com.godeltech.gorodetskaya.task.services.impl;

import com.godeltech.gorodetskaya.task.dao.api.Dao;
import com.godeltech.gorodetskaya.task.entity.Author;
import com.godeltech.gorodetskaya.task.entity.Book;
import com.godeltech.gorodetskaya.task.exception_handling.NoSuchEntityException;
import com.godeltech.gorodetskaya.task.exception_handling.RepeatAddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Service
public class BookServiceImpl implements com.godeltech.gorodetskaya.task.services.api.Service<Book> {

    @Autowired
    @Qualifier("bookDaoImpl")
    private Dao<Book> dao;

    @Autowired
    private AuthorServiceImpl authorService;

    @Override
    public Map<Integer, Book> getAllItems() {
        return dao.getAllItems();
    }

    @Override
    public Book getItemById(int id) {
        Optional<Book> optional = dao.getItemById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchEntityException("There is no book with id = " + id + " in directory");
        }
    }

    @Override
    public Book addItem(Book book) {
        if ((getAllItems().values().stream().noneMatch(element -> element.equals(book)))) {
            book.getAuthors().forEach(author -> authorService.addItem(author));
            return dao.addItem(book);
        } else {
            throw new RepeatAddingException("Element has already been created");
        }
    }

    @Override
    public Book updateItem(Book book) {
        book.getAuthors().forEach(author -> authorService.updateItem(author));
        return dao.updateItem(book);
    }

    @Override
    public void deleteItem(int id) {
        getItemById(id);
        dao.deleteItem(id);
    }

    public void deleteBooksByAuthorId(int id) {
        dao.deleteBooksByAuthorId(id);
    }
}