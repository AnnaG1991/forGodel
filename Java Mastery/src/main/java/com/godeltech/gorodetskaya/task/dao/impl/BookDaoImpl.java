package com.godeltech.gorodetskaya.task.dao.impl;

import com.godeltech.gorodetskaya.task.config.DatabaseConnector;
import com.godeltech.gorodetskaya.task.dao.api.Dao;
import com.godeltech.gorodetskaya.task.entity.Author;
import com.godeltech.gorodetskaya.task.entity.Book;
import com.godeltech.gorodetskaya.task.entity.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;


@Repository
public class BookDaoImpl implements Dao<Book> {

    @Autowired
    DatabaseConnector connector;

    @Override
    public Map<Integer, Book> getAllItems() {
        Map<Integer, Book> catalog = new HashMap<>();
        Author author = null;
        Book book = null;
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery
                    ("SELECT BOOK.ID, BOOK.TITLE, BOOK.YEAR_OF_PUBLICATION, BOOK.PUBLISHER," +
                            " AUTHOR.ID, AUTHOR.NAME, AUTHOR.SURNAME, AUTHOR.DATE_OF_BIRTH, AUTHOR.GENDER" +
                            " FROM BOOK JOIN BOOK_AUTHOR ON BOOK.ID=BOOK_AUTHOR.BOOK_ID" +
                            " JOIN AUTHOR ON BOOK_AUTHOR.AUTHOR_ID=AUTHOR.ID");
            while (resultSet.next()) {
                book = new Book();
                List<Author> authors = new ArrayList<>();

                author = new Author();
                author.setId(resultSet.getInt("AUTHOR.ID"));
                author.setName(resultSet.getString("NAME"));
                author.setSurname(resultSet.getString("SURNAME"));
                author.setDateOfBirth(resultSet.getString("DATE_OF_BIRTH"));
                author.setGender(Gender.lookUp(resultSet.getString("GENDER")));
                authors.add(author);
                book.setAuthors(authors);

                book.setId(resultSet.getInt("BOOK.ID"));
                book.setTitle(resultSet.getString("TITLE"));
                book.setYearOfPublication(resultSet.getString("YEAR_OF_PUBLICATION"));
                book.setPublisher(resultSet.getString("PUBLISHER"));

                if (!catalog.containsValue(book)) {
                    catalog.put(book.getId(), book);
                } else {
                    catalog.get(book.getId()).getAuthors().add(book.getAuthors().get(0));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return catalog;
    }

    @Override
    public Optional<Book> getItemById(int id) {
        Book book = null;
        Author author = null;
        List<Author> authors = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT BOOK.TITLE, BOOK.YEAR_OF_PUBLICATION, BOOK.PUBLISHER," +
                             " AUTHOR.ID, AUTHOR.NAME, AUTHOR.SURNAME, AUTHOR.DATE_OF_BIRTH, AUTHOR.GENDER" +
                             " FROM BOOK JOIN BOOK_AUTHOR ON BOOK.ID=BOOK_AUTHOR.BOOK_ID" +
                             " JOIN AUTHOR ON BOOK_AUTHOR.AUTHOR_ID=AUTHOR.ID" +
                             " WHERE BOOK.ID=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = new Book();
                book.setId(id);
                book.setTitle(resultSet.getString("TITLE"));
                book.setYearOfPublication(resultSet.getString("YEAR_OF_PUBLICATION"));
                book.setPublisher(resultSet.getString("PUBLISHER"));

                author = new Author();
                author.setId(resultSet.getInt("ID"));
                author.setName(resultSet.getString("NAME"));
                author.setSurname(resultSet.getString("SURNAME"));
                author.setDateOfBirth(resultSet.getString("DATE_OF_BIRTH"));
                author.setGender(Gender.lookUp(resultSet.getString("GENDER")));
                authors.add(author);
                book.setAuthors(authors);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public Book addItem(Book book) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO BOOK (TITLE, " +
                     "YEAR_OF_PUBLICATION, PUBLISHER) VALUES (?, ?, ?)");
             PreparedStatement preparedStatementSelect = connection.prepareStatement("SELECT ID FROM BOOK WHERE " +
                     "TITLE=?")) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getYearOfPublication());
            preparedStatement.setString(3, book.getPublisher());
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

    @Override
    public Book updateItem(Book book) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE BOOK SET TITLE=?, " +
                     "YEAR_OF_PUBLICATION=?, PUBLISHER=? WHERE ID=?");
             PreparedStatement preparedStatementSelect = connection.prepareStatement("SELECT TITLE, " +
                     "YEAR_OF_PUBLICATION, PUBLISHER FROM BOOK WHERE ID=?")) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getYearOfPublication());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setInt(4, book.getId());
            preparedStatement.execute();
            preparedStatementSelect.setInt(1, book.getId());
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
                book.setTitle(resultSet.getString("TITLE"));
                book.setYearOfPublication(resultSet.getString("YEAR_OF_PUBLICATION"));
                book.setPublisher(resultSet.getString("PUBLISHER"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return book;
    }

    @Override
    public void deleteItem(int id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE BOOK WHERE ID=?");
             PreparedStatement statement = connection.prepareStatement("DELETE BOOK_AUTHOR WHERE BOOK_ID=?")) {
            statement.setInt(1, id);
            statement.execute();
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteBooksByAuthorId(int id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT BOOK.ID FROM BOOK_AUTHOR JOIN BOOK " +
                     "ON BOOK_AUTHOR.BOOK_ID=BOOK.ID WHERE BOOK_AUTHOR.AUTHOR_ID = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("BOOK.ID");
                deleteItem(bookId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}