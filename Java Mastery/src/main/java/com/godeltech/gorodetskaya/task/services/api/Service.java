package com.godeltech.gorodetskaya.task.services.api;

import java.util.List;

public interface Service<T> {

    List<T> getAllItems();

    T getItemById(int id);

    T addItem(T t);

    T updateItem(T t);

    void deleteItem(int id);
}