package com.godeltech.gorodetskaya.task.services.api;

import java.util.List;
import java.util.Map;

public interface Service<T> {

    Map<Integer, T> getAllItems();

    T getItemById(int id);

    T addItem(T t);

    T updateItem(T t);

    void deleteItem(int id);
}