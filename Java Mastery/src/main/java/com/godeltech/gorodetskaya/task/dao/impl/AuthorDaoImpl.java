package com.godeltech.gorodetskaya.task.dao.impl;

import com.godeltech.gorodetskaya.task.config.DatabaseConnector;
import com.godeltech.gorodetskaya.task.dao.api.Dao;
import com.godeltech.gorodetskaya.task.entity.Author;
import com.godeltech.gorodetskaya.task.entity.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class AuthorDaoImpl implements Dao<Author> {

    @Autowired
    DatabaseConnector connector;

    @Override
    public Map<Integer, Author> getAllItems() {
        Map<Integer, Author> catalog = new HashMap<>();
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT ID, NAME, SURNAME, DATE_OF_BIRTH, GENDER FROM AUTHOR");
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt("ID"));
                author.setName(resultSet.getString("NAME"));
                author.setSurname(resultSet.getString("SURNAME"));
                author.setDateOfBirth(resultSet.getString("DATE_OF_BIRTH"));
                author.setGender(Gender.lookUp(resultSet.getString("GENDER")));
                catalog.put(author.getId(), author);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return catalog;
    }

    @Override
    public Optional<Author> getItemById(int id) {
        Author author = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT NAME, SURNAME, DATE_OF_BIRTH, GENDER FROM AUTHOR WHERE ID=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                author = new Author();
                author.setId(id);
                author.setName(resultSet.getString("NAME"));
                author.setSurname(resultSet.getString("SURNAME"));
                author.setDateOfBirth(resultSet.getString("DATE_OF_BIRTH"));
                author.setGender(Gender.lookUp(resultSet.getString("GENDER")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(author);
    }

    @Override
    public Author addItem(Author author) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO AUTHOR (NAME, SURNAME, DATE_OF_BIRTH, GENDER) VALUES (?, ?, ?, ?)");
             PreparedStatement preparedStatementSelect = connection.prepareStatement("SELECT ID FROM AUTHOR WHERE SURNAME=?")) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getSurname());
            preparedStatement.setString(3, author.getDateOfBirth());
            preparedStatement.setString(4, author.getGender().getString());
            preparedStatement.execute();
            preparedStatementSelect.setString(1, author.getSurname());
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
                author.setId(resultSet.getInt("ID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return author;
    }

    @Override
    public Author updateItem(Author author) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE AUTHOR SET NAME=?, SURNAME=?, DATE_OF_BIRTH=?, GENDER=? WHERE ID=?");
             PreparedStatement preparedStatementSelect = connection.prepareStatement("SELECT NAME, SURNAME, DATE_OF_BIRTH, GENDER FROM AUTHOR WHERE ID=?")) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getSurname());
            preparedStatement.setString(3, author.getDateOfBirth());
            preparedStatement.setString(4, author.getGender().getString());
            preparedStatement.setInt(5, author.getId());
            preparedStatement.execute();
            preparedStatementSelect.setInt(1, author.getId());
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
                author.setName(resultSet.getString("NAME"));
                author.setSurname(resultSet.getString("SURNAME"));
                author.setDateOfBirth(resultSet.getString("DATE_OF_BIRTH"));
                author.setGender(Gender.lookUp(resultSet.getString("GENDER")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return author;
    }

    public void deleteItem(int id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE AUTHOR WHERE ID=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}