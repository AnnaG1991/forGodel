package com.godeltech.gorodetskaya.task.services;

import com.godeltech.gorodetskaya.task.dao.BookDao;
import com.godeltech.gorodetskaya.task.entity.Book;
import com.godeltech.gorodetskaya.task.exception_handing.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public Book getBookById(int id) {
        Optional<Book> optional = bookDao.getBookById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NoSuchEntityException("There is no book with id = " + id + " in directory");
        }
    }

    public Book addBook(Book book){
        return bookDao.addBook(book);
    }

    public Book updateBook(Book book){
        return bookDao.updateBook(book);
    }

    public void deleteBook(int id){
        getBookById(id);
        bookDao.deleteBook(id);
    }
}