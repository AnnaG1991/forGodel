package com.godeltech.gorodetskaya.task.dao.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<T> {

    Map<Integer, T> getAllItems();

    Optional<T> getItemById(int id);

    T addItem(T t);

    T updateItem(T t);

    void deleteItem(int id);

    default void deleteBooksByAuthorId(int id) {
    }
}