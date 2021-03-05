package com.godeltech.gorodetskaya.task.services.impl;

import com.godeltech.gorodetskaya.task.dao.api.Dao;
import com.godeltech.gorodetskaya.task.entity.Author;
import com.godeltech.gorodetskaya.task.exception_handling.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class AuthorServiceImpl implements com.godeltech.gorodetskaya.task.services.api.Service<Author> {

    @Autowired
    @Qualifier("authorDaoImpl")
    private Dao<Author> dao;

    @Autowired
    private BookServiceImpl bookService;


    @Override
    public List<Author> getAllItems() {
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
        return dao.addItem(author);
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

