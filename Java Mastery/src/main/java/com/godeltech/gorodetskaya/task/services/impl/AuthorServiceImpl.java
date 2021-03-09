package com.godeltech.gorodetskaya.task.services.impl;

import com.godeltech.gorodetskaya.task.dao.api.Dao;
import com.godeltech.gorodetskaya.task.entity.Author;
import com.godeltech.gorodetskaya.task.exception_handling.NoSuchEntityException;
import com.godeltech.gorodetskaya.task.exception_handling.RepeatAddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Service
public class AuthorServiceImpl implements com.godeltech.gorodetskaya.task.services.api.Service<Author> {

    @Autowired
    @Qualifier("authorDaoImpl")
    private Dao<Author> dao;

    @Autowired
    private BookServiceImpl bookService;


    @Override
    public Map<Integer, Author> getAllItems() {
        return dao.getAllItems();
    }

    @Override
    public Author getItemById(int id) {
        Optional<Author> optional = dao.getItemById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchEntityException("There is no author with id = " + id + " in directory");
        }
    }

    @Override
    public Author addItem(Author author) {
        if (getAllItems().values().stream().noneMatch(element -> element.equals(author))) {
            return dao.addItem(author);
        } else {
            throw new RepeatAddingException("Element has already been created");
        }
    }

    @Override
    public Author updateItem(Author author) {
        return dao.updateItem(author);
    }

    @Override
    public void deleteItem(int id) {
        getItemById(id);
        bookService.deleteBooksByAuthorId(id);
        dao.deleteItem(id);
    }
}