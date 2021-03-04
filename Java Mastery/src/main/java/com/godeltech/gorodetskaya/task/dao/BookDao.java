package com.godeltech.gorodetskaya.task.dao;

import com.godeltech.gorodetskaya.task.config.DatabaseConnector;
import com.godeltech.gorodetskaya.task.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDao {

    @Autowired
    DatabaseConnector connector;

    public List<Book> getAllBooks() {
        List<Book> catalog = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT ID, TITLE, YEAR_OF_PUBLICATION FROM BOOK");
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("ID"));
                book.setTitle(resultSet.getString("TITLE"));
                book.setYearOfPublication(resultSet.getString("YEAR_OF_PUBLICATION"));
                catalog.add(book);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return catalog;
    }

    public Optional<Book> getBookById(int id) {
        Book book = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT TITLE, YEAR_OF_PUBLICATION FROM BOOK WHERE ID=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = new Book();
                book.setId(id);
                book.setTitle(resultSet.getString("TITLE"));
                book.setYearOfPublication(resultSet.getString("YEAR_OF_PUBLICATION"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    public Book addBook(Book book) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO BOOK (TITLE, YEAR_OF_PUBLICATION) VALUES (?, ?)");
             PreparedStatement preparedStatementSelect = connection.prepareStatement("SELECT ID FROM BOOK WHERE TITLE=?")) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getYearOfPublication());
            preparedStatement.execute();
            preparedStatementSelect.setString(1, book.getTitle());
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
               book.setId(resultSet.getInt("ID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return book;
    }

    public Book updateBook(Book book) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE BOOK SET TITLE=?, YEAR_OF_PUBLICATION=? WHERE ID=?");
             PreparedStatement preparedStatementSelect = connection.prepareStatement("SELECT TITLE, YEAR_OF_PUBLICATION FROM BOOK WHERE ID=?")) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getYearOfPublication());
            preparedStatement.setInt(3, book.getId());
            preparedStatement.execute();
            preparedStatementSelect.setInt(1, book.getId());
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
                book.setTitle(resultSet.getString("TITLE"));
                book.setYearOfPublication(resultSet.getString("YEAR_OF_PUBLICATION"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return book;
    }

    public void deleteBook(int id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE BOOK WHERE ID=?")){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}