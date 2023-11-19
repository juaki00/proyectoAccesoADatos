package com.example;

import java.util.List;

public interface DAO<T> {

    public List<T> getAll();
    public T get(Long id);
    public T save(T data);
    public void update(T data);
    public void delete(T data);
}
